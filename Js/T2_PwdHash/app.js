const bcrypt = require('bcrypt');
const argon2 = require('argon2');
const crypto = require('crypto');
const { Console } = require('console');

const password = "Mi contraseña";

(async () => { 
    let salt = await bcrypt.genSalt(10)
    const hashes = {
        salt,
        bcrypt10: await bcrypt.hash(password,10),
        bcryptSalt: await bcrypt.hash(password,salt),
        bcryptSalt22: await bcrypt.hash(password,'$2b$10$0987654321098765432101'),
        argon2: await argon2.hash(password),
        custom: customHash(password,10)
    };
    console.log(hashes);
    const verify = {
        bcrypt10: await bcrypt.compare(password, hashes.bcrypt10),
        bcryptSalt: await bcrypt.compare(password, hashes.bcryptSalt),
        bcryptSalt22: await bcrypt.compare(password, hashes.bcryptSalt22),
        argon2: await argon2.verify(hashes.argon2, password),
        custom: verifyHash(password, hashes.custom),
        customFalse: verifyHash(password+'a', hashes.custom),
    };
    console.log(verify);
})();

function customHash(input, rounds, salt) {
    let totalRounds = Math.pow(2,rounds);
    let hash = input;
    salt = salt || Math.floor(Math.random() * 0x7fffffff).toString(36);

    for(let i=0; i<totalRounds; i++) {
        hash = crypto.createHash('md5').update(salt + hash).digest('binary');
    }
    return `$custom$${rounds}$${btoa(salt)}$${btoa(hash)}`;
}


function verifyHash(input, hash) {
    if (!hash.startsWith('$custom')){
        console.error('Invalid hash');
        return;
    }
    let [_, _algo, rounds, salt, _hash] = hash.split('$');
    if (rounds < 0) {
        console.error('Invalid rounds');
    }
    salt = atob(salt);
    if (salt.length < 1) {
        console.error('Invalid Sal')
    }
    const testHash = customHash(input, rounds, salt);
    return hash === testHash;
}