/* add as magias da Home que o Francisco fez */

 document.addEventListener("DOMContentLoaded", () => {
        const tr = document.getElementsByClassName("container");
        const tps = document.getElementsByClassName("topicos");
        const track = tr[0];
        const tp = tps[0];
        track.classList.add("invisivel");
        const topicos = document.querySelectorAll(".topicos_icon");
        const exit = document.getElementById("exit");
        const niveis = document.querySelectorAll(".nivel");

        topicos.forEach(topico => {
            topico.addEventListener("click", () => {
                tp.classList.add("invisivel");
                track.classList.remove("invisivel");
                iniciarPosicaoOrdo();

            });
        });

        exit.addEventListener("click", () => {
            tp.classList.remove("invisivel");
            track.classList.add("invisivel");
        })

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



        const ordo = document.getElementById("ordo");
        const mapa = document.querySelector(".map");
        const atividades = document.querySelectorAll(".flag, .tile");

        function moverOrdoPara(elemento) {
            if (!elemento || !mapa) return;

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
            // Seleciona apenas as atividades que possuem a classe 'concluido'
            const concluidas = document.querySelectorAll(".flag");

            let atividadeInicial;

            if (concluidas.length > 0) {
                // Se existirem atividades concluídas, pega a ÚLTIMA do array [tamanho - 1]
                atividadeInicial = concluidas[concluidas.length - 1];
            } else if (atividades.length > 0) {
                // Se não houver nenhuma concluída (usuário novo), começa na primeiríssima
                atividadeInicial = atividades[0];
            }

            if (atividadeInicial) {
                // Timeout de 200ms para garantir que o layout do monitor renderizou 100%
                setTimeout(() => moverOrdoPara(atividadeInicial), 200);
            }
        }

        // Executa a busca assim que a página carrega
        iniciarPosicaoOrdo();

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
    });