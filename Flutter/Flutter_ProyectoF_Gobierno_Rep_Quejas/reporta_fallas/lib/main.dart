import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:geolocator/geolocator.dart';
import 'package:image_picker/image_picker.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Reporta Fallas',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: ReportForm(),
    );
  }
}

class ReportForm extends StatefulWidget {
  @override
  _ReportFormState createState() => _ReportFormState();
}

class _ReportFormState extends State<ReportForm> {
  final _formKey = GlobalKey<FormBuilderState>();
  XFile? _pickedImage;
  Position? _currentPosition;
  bool _isSubmitting = false;

  Future<void> _pickImage() async {
    final ImagePicker picker = ImagePicker();
    final XFile? image = await picker.pickImage(source: ImageSource.camera);
    if (image != null) {
      setState(() {
        _pickedImage = image;
      });
    }
  }

  Future<void> _getCurrentLocation() async {
    bool serviceEnabled = await Geolocator.isLocationServiceEnabled();
    if (!serviceEnabled) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Activa el GPS para continuar')),
      );
      return;
    }

    LocationPermission permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
      if (permission == LocationPermission.denied) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Permiso de ubicación denegado')),
        );
        return;
      }
    }

    Position position = await Geolocator.getCurrentPosition(
        desiredAccuracy: LocationAccuracy.high);
    setState(() {
      _currentPosition = position;
    });
  }

  Future<String?> _uploadImage(XFile image) async {
    try {
      final storageRef = FirebaseStorage.instance
          .ref()
          .child('reportes')
          .child('${DateTime.now().millisecondsSinceEpoch}.jpg');
      await storageRef.putFile(File(image.path));
      return await storageRef.getDownloadURL();
    } catch (e) {
      print('Error subiendo imagen: $e');
      return null;
    }
  }

  Future<void> _submit() async {
    if (_formKey.currentState?.saveAndValidate() ?? false) {
      setState(() {
        _isSubmitting = true;
      });

      final data = _formKey.currentState!.value;
      String descripcion = data['descripcion'];
      String? imageUrl;
      double? lat;
      double? lon;

      if (_pickedImage != null) {
        imageUrl = await _uploadImage(_pickedImage!);
      }

      if (_currentPosition != null) {
        lat = _currentPosition!.latitude;
        lon = _currentPosition!.longitude;
      }

      await FirebaseFirestore.instance.collection('reportes').add({
        'descripcion': descripcion,
        'imagen': imageUrl,
        'latitud': lat,
        'longitud': lon,
        'timestamp': FieldValue.serverTimestamp(),
      });

      setState(() {
        _isSubmitting = false;
        _pickedImage = null;
        _currentPosition = null;
      });

      _formKey.currentState?.reset();

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Reporte enviado correctamente')),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Formulario inválido')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Reporta Fallas')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: FormBuilder(
            key: _formKey,
            child: Column(
              children: [
                FormBuilderTextField(
                  name: 'descripcion',
                  decoration: InputDecoration(
                    labelText: 'Descripción de la falla',
                    border: OutlineInputBorder(),
                  ),
                  maxLines: 4,
                  validator: FormBuilderValidators.compose([
                    FormBuilderValidators.required(),
                    FormBuilderValidators.minLength(10),
                  ]),
                ),
                SizedBox(height: 20),
                ElevatedButton.icon(
                  icon: Icon(Icons.photo_camera),
                  label: Text('Tomar foto'),
                  onPressed: _pickImage,
                ),
                if (_pickedImage != null)
                  Padding(
                    padding: const EdgeInsets.only(top: 10),
                    child: Image.file(
                      File(_pickedImage!.path),
                      height: 150,
                    ),
                  ),
                SizedBox(height: 20),
                ElevatedButton.icon(
                  icon: Icon(Icons.location_on),
                  label: Text('Obtener ubicación'),
                  onPressed: _getCurrentLocation,
                ),
                if (_currentPosition != null)
                  Padding(
                    padding: const EdgeInsets.only(top: 10),
                    child: Text(
                      'Lat: ${_currentPosition!.latitude}, Lon: ${_currentPosition!.longitude}',
                    ),
                  ),
                SizedBox(height: 30),
                ElevatedButton(
                  onPressed: _isSubmitting ? null : _submit,
                  child: _isSubmitting
                      ? CircularProgressIndicator()
                      : Text('Enviar reporte'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
