/* add as magias da Home que o Francisco fez */
// Cara era real magia negra que o Francisco fez
// O Francisco foi longe demais por esse projeto...
const tr = document.getElementById("track-container");
const tps = document.getElementById("topicos-container");
const topicos = document.querySelectorAll(".topicos_icon");
const niveis = document.querySelectorAll(".nivel");
const ordo = document.getElementById("ordo");
const mapa = document.querySelector(".map");
const atividades = document.querySelectorAll(".flag, .tile");

function exit() {
    tps.classList.remove("invisivel");
    tr.classList.add("invisivel");
}

if(document.body.dataset.topico !== null && document.body.dataset.topico !== "nenhum" && typeof document.body.dataset.topico !== "undefined") {
    console.log(document.body.dataset.topico)

    tps.classList.add("invisivel");
    tr.classList.remove("invisivel");

    niveis.item(0).classList.replace("nivel_n_sel","nivel_sel")
    niveis.forEach(nivel => {
        nivel.addEventListener("click", () => {
            if(!nivel.classList.contains("nivel_sel")){
                var niveis_sel = document.getElementsByClassName("nivel_sel");
                var nivel_sel = niveis_sel[0];
                nivel_sel.classList.replace("nivel_sel", "nivel_n_sel");
                nivel.classList.replace("nivel_n_sel", "nivel_sel");
                iniciarPosicaoOrdo(); //trocar a track dependendo do nivel
            }
        });
    });
    iniciarPosicaoOrdo();

    function moverOrdoPara(elemento) {

        // Encontra o grid que envelopa essa atividade específica
        const gridPai = elemento.closest('.grid');
        if (!gridPai) return;

        // Cálcula a posição fixa combinando a posição do Grid + a posição da atividade
        const topoReal = gridPai.offsetTop + elemento.offsetTop;
        const esquerdaReal = gridPai.offsetLeft + elemento.offsetLeft;

        // Centraliza o Ordo exatamente no meio da bolinha da atividade
        const topoCalculado = topoReal + (elemento.offsetHeight / 2) - (ordo.offsetHeight / 2);
        const esquerdaCalculada = esquerdaReal + (elemento.offsetWidth / 2) - (ordo.offsetWidth / 2);

        // Aplica os valores direto no style do Ordo
        ordo.style.top = `${topoCalculado}px`;
        ordo.style.left = `${esquerdaCalculada}px`;

        // Guarda qual é a atividade atual para o evento de resize usar depois
        ordo.dataset.atividadeAtualId = elemento.getAttribute('data-fase') || '0';
    }

    function iniciarPosicaoOrdo() {
        let atividadesGuardadas

        atividades.forEach(atividade => {
            const atividadeNivel = atividade.firstElementChild.dataset.nivel
            const nivelAtual = document.querySelector(".nivel_sel").innerText

            if(atividadeNivel !== nivelAtual) {
                atividade.parentElement.style.display = "none"
            } else {
                atividade.parentElement.style.display = "block"
            }
        })

        // Seleciona apenas as atividades que possuem a classe 'concluido'
        const concluidas = document.querySelectorAll(".flag");

        let atividadeInicial;

        if (concluidas.length > 0) {
            // Se existirem atividades concluídas, pega a ÚLTIMA do array [tamanho - 1]
            atividadeInicial = concluidas[concluidas.length - 1];
        } else {
            // Se não houver nenhuma concluída (usuário novo), começa na primeiríssima
            atividadeInicial = null

            for (const atividade of atividades) {
                if (atividade.parentElement.style.display !== 'none') {
                    atividadeInicial = atividade
                    break
                }
            }
        }

        console.log(atividadeInicial)
        moverOrdoPara(atividadeInicial)
    }


    // Adiciona o evento de clique em cada atividade do mapa
    atividades.forEach(atividade => {
        atividade.addEventListener("click", () => {
            moverOrdoPara(atividade);
        });
    });

    // Reajusta o boneco se a tela mudar de tamanho (evita que ele fique flutuando fora do lugar)
    window.addEventListener("resize", () => {
        const idAtual = ordo.dataset.atividadeAtualId;
        let elementoAlvo = document.querySelector(`[data-fase="${idAtual}"]`);

        if (!elementoAlvo && atividades.length > 0) {
            elementoAlvo = atividades[0];
        }

        if (elementoAlvo) moverOrdoPara(elementoAlvo);
    });

    atividades.forEach(atividade => {
        atividade.addEventListener("dblclick", () => {
            window.location.replace("faq.html"); //redireciona(agora só redirecionar para a tela de atividades)
        });
    });
}