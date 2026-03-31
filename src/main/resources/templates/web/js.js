const API_BASE_URL = 'http://localhost:8080';

// Função para gerar UUID v4 no frontend
function generateUUID() { // Public Domain/MIT
    let d = new Date().getTime();//Timestamp
    let d2 = ((typeof performance !== 'undefined') && performance.now && (performance.now()*1000)) || 0;//Time in microseconds since page-load or 0 if unsupported
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        let r = Math.random() * 16;//random number between 0 and 16
        if(d > 0){
            r = (d + r)%16 | 0;
            d = Math.floor(d/16);
        } else {
            r = (d2 + r)%16 | 0;
            d2 = Math.floor(d2/16);
        }
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}

function showResult(message, isError = false) {
    const resultDiv = document.getElementById('result-message');
    resultDiv.textContent = message;
    resultDiv.classList.remove('hidden', 'msg-success', 'msg-error');
    resultDiv.classList.add(isError ? 'msg-error' : 'msg-success');
}

// Inicializar DB (SEED)
document.getElementById('seed-btn').addEventListener('click', async () => {
    try {
        const res = await fetch(`${API_BASE_URL}/seed`, { method: 'POST' });
        if (res.ok) {
            showResult('Database inicializado com sucesso (Seed OK).');
        } else {
            showResult('Erro ao inicializar DB (Pode já estar criado/Seed feito).', true);
        }
    } catch (error) {
        showResult(`Falha na conexão: ${error.message}`, true);
    }
});

// Envio de transação
document.getElementById('transaction-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const senderId = document.getElementById('senderId').value;
    const receiverId = document.getElementById('receiverId').value;
    const value = document.getElementById('value').value;
    const category = document.getElementById('category').value;
    const interpotecyKey = generateUUID();

    const payload = {
        senderId: parseInt(senderId),
        receiverId: parseInt(receiverId),
        value: parseFloat(value),
        catorgory: category,
        interpotecyKey: interpotecyKey 
    };

    try {
        const response = await fetch(`${API_BASE_URL}/transaction`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        const data = await response.json();

        if (response.ok) {
            showResult(`Sucesso: ${data.response} | Valor: R$${data.value} | Categoria: ${data.category} | Temp: ${data.transactionTime}`);
            e.target.reset(); // Limpa form
        } else {
            // Caso o backend retorne o DTO de erro (status 400 ou 500)
            showResult(`Erro: ${data.response || 'Falha na requisição'}`, true);
        }
    } catch (error) {
        showResult(`Erro na comunicação com servidor: ${error.message}. (Verifique se o backend tem CORS abilitado no Controller)`, true);
    }
});