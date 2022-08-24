
// Demo
let count  = 0;

const btn = document.querySelector('.tile');

function shuffle(elementID) {
    let elem = document.getElementById(elementID);
    elem.classList.add('spin')
    setTimeout(() => {  elem.innerText = randAlphabet(); }, 500);
    setTimeout(() => {  elem.classList.remove('spin'); }, 1500); 
}

const randAlphabet = () => {
    const alphabet = "ABCDEFGHIJKLMNOOPQRSTUVWXYZ";
    return alphabet[Math.floor(Math.random() * alphabet.length)];
}


