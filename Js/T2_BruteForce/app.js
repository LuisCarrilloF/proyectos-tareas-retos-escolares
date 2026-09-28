const readline = require('readline');
const crypto = require('crypto');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

const handleResponse = (hash) =>{
    hash = hash.trim();
    const startTime = Date.now();
    let encontrado = diccionario[hash] || fuerzaBruta(hash);
    const endTime = Date.now();
    const elapsedTime = (endTime - startTime) / 1000;
    console.log(`El tiempo de ejecucion es de:  ${elapsedTime} segundos`);
    if (encontrado){
        console.log(`El texto original es: ${encontrado}`);
    }
   // rl.close();
}
(async () => {
    do{
        let hash = await new Promise(
            resolve => rl.question('Introducir el hash: ', resolve)
        ); 
        handleResponse(hash);
    }while(true);
})();

const diccionario = {};

function hashMd5(string) {
    let hash = crypto.createHash('md5').update(string).digest('hex');
    diccionario[hash] = string;
    return hash;
}

const alfabeto = '0123456789';
const longitud = 5;

function fuerzaBruta(hash, texto = ''){
    if(texto.length > longitud) return;

    if (hash === hashMd5(texto)) {
        return texto;
    }
    let encontrado;
    for (let char of alfabeto) {
        if (encontrado = fuerzaBruta(hash, texto + char)) {
            return encontrado;
        }
    }

}