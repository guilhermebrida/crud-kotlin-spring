function updateUser(event){
    const userID = (event.target).getAttribute("data-user-id");
    console.log(userID);
    fetch(`/update/${userID}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao chamar o endpoint');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            window.location.href = `/details/${userID}`
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}
function deleteUser(event){
    const userID = (event.target).getAttribute("data-user-id");
    console.log(userID);
    fetch(`/delete/${userID}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao chamar o endpoint');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            window.location.href = "/users";
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}

function toggleRow(event,row) {

    var hiddenRow = row.nextElementSibling;
    hiddenRow.classList.toggle('hide');

    if (hiddenRow.classList.contains('hide')) {
        hiddenRow.querySelectorAll('td').forEach( td => td.style.display = 'none');

        //hiddenRow.querySelectorAll('td').style.display = 'none';
    } else {
        hiddenRow.querySelectorAll('td').forEach( td => td.style.display = 'table-cell');
        //hiddenRow.querySelector('td').style.display = 'table-cell';
    }
}