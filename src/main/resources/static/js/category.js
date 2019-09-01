getCategoryFromServer(numberCategory);

function getCategoryFromServer(numberCategory) {
    var url = serverURL + "rest/question/" + numberCategory;
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if (this.status === 200 && this.readyState === 4) {
            var categories = JSON.parse(this.responseText);
            var target = document.getElementById('in');

            for (var i = 0; i < categories.length; i++) {
                target.innerHTML += "<li id='category" + i + "' onclick='selectCategory(" + i + ")' class='btn btn-secondary'>" + categories[i] + "</li>";
            }
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}


function selectCategory(id) {
    var category = document.getElementById("category" + id).innerText;
    window.location.href = serverURL + "getquestion?category=" + category;
}
