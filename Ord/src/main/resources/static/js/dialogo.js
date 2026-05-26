function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function ordofalar(texto, balaoIndex) {
    const baloes = document.getElementsByClassName("balao");
    const balao = baloes[balaoIndex];
    
    if (!balao) {
        console.error(`Balão no índice ${balaoIndex} não foi encontrado.`);
        return;
    }
    
    await sleep(1000);
    
    balao.classList.add("balao-fala");
    balao.classList.remove("hidden");
    
    const elementoTexto = balao.querySelector("p") || balao.lastElementChild;
    elementoTexto.innerHTML = ""; 
    
    await typeWriter(texto, elementoTexto, 0, 50);
    
    const tempoLeitura = texto.length * 50;
    await sleep(tempoLeitura);
    
    balao.classList.add("hidden");
    balao.classList.remove("balao-fala");
    elementoTexto.innerHTML = "";
    await sleep(4000);
}  

function typeWriter(texto, elemento, index, velocidade) {     
    return new Promise((resolve) => {
        function digitar() {
            if (index < texto.length) {
                elemento.innerHTML += texto.charAt(index);
                index++;
                setTimeout(digitar, velocidade);
            } else {
                resolve();
            }
        }
        digitar();
    });
}