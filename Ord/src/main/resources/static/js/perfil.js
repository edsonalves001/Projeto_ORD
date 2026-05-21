// Sinto que a Milena chapou, mas se quiserem usar isso se n der tempo de fazer as funcionalidades da de perfil, ok?

const searchInput = document.getElementById('searchUser');
        const searchResult = document.getElementById('searchResult');

        const usuariosExemplo = [
            'Ronaldo Silva', 'Maria Santos', 'João Pereira',
            'Ana Costa', 'Pedro Lima', 'Carla Souza'
        ];

        function buscarAmigo() {
            const termo = searchInput.value.toLowerCase().trim();
            if (termo.length < 2) {
                searchResult.innerHTML = `
                    <div class="resultado-linha"><span>Ronaldo</span><button class="btn-adicionar">Adicionar</button></div>
                    <div class="resultado-linha"><span>Ronaldo</span><button class="btn-adicionar">Adicionar</button></div>
                    <div class="resultado-linha"><span>Ronaldo</span><button class="btn-adicionar">Adicionar</button></div>
                `;
                adicionarEventosBotoes();
                return;
            }

            const filtrados = usuariosExemplo.filter(u => u.toLowerCase().includes(termo));
            if (filtrados.length > 0) {
                searchResult.innerHTML = filtrados.map(nome => `
                    <div class="resultado-linha">
                        <span>${nome}</span>
                        <button class="btn-adicionar" data-nome="${nome}">Adicionar</button>
                    </div>
                `).join('');
            } else {
                searchResult.innerHTML = '<div class="resultado-linha">Nenhum usuário encontrado</div>';
            }
            adicionarEventosBotoes();
        }

        function adicionarEventosBotoes() {
            document.querySelectorAll('.btn-adicionar').forEach(btn => {
                btn.removeEventListener('click', handleAdicionar);
                btn.addEventListener('click', handleAdicionar);
            });
        }

        function handleAdicionar(e) {
            const nome = e.target.getAttribute('data-nome') || e.target.previousSibling?.innerText || 'Usuário';
            alert(`Solicitação de amizade enviada para ${nome}!`);
        }

        searchInput.addEventListener('keyup', (e) => {
            buscarAmigo();
        });

        document.querySelector('.btn-encontrar')?.addEventListener('click', () => {
            searchInput.focus();
        });

        document.querySelector('.btn-desafiar')?.addEventListener('click', () => {
            alert('Desafie um amigo para uma competição de estudos!');
        });

        document.querySelector('.link-mudar-senha')?.addEventListener('click', (e) => {
            e.preventDefault();
            alert('Redirecionar para página de alteração de senha');
        });

        adicionarEventosBotoes();