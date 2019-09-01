let bar = document.getElementById("myBar");
let width = 100;

let int = setInterval(function(){
    if (width <= 0) {
        clearInterval(int);
        Swal.fire({
            position: 'center',
            type: 'error',
            title: 'Time out',
            showConfirmButton: false,
            timer: 3000,
        });
        select(-1);
        setTimeout(function() {
            location.reload();
        }, 3000);
    }
    width -= 0.2;
    bar.style.width = width + "%";
}, questionTime * 2);

function fifty_fifty() {
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if (this.status === 200 && this.readyState === 4) {
            let helps = document.getElementsByClassName("help");
            for (let i = 0; i < helps.length; i++) {
                helps[i].style.display = "none";
            }

            let h1 = document.getElementsByClassName("kjut");
            for (let i = 0; i < h1.length; i++) {
                h1[i].style.opacity = "0.2";
            }
            let resp = JSON.parse(this.responseText);
            for (let i = 0; i < resp.length; i++) {
                document.getElementById(resp[i]).style.border = "solid 6px #ffd400";
                document.getElementById(resp[i]).style.opacity = "1";
            }
        }
    };
    xml.open("GET", serverURL + 'fifty-fifty');
    xml.send();
}

function publikum(){
    let e = document.getElementsByClassName("percentage");
    let num = 100;
    for (let i = 0; i < e.length; i++){
        e[i].classList.remove("hiden");
        let n = Math.floor(Math.random() * num);
        e[i].innerText = n + "%";
        if (i === 3) {
            e[i].innerText = num + "%";
        }
        num -= n;
    }
    let helps = document.getElementsByClassName("help");
    for (let i = 0; i < helps.length; i++) {
        helps[i].style.display = "none";
    }
}

function select(id) {
    clearInterval(int);
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if (this.status === 200 && this.readyState === 4) {
            let resp = JSON.parse(this.responseText);
            document.getElementById(resp).style.border = "solid 2px green";
            if (resp != id) {
                document.getElementById(id).style.border = "solid 2px red";
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: 'Wrong',
                    timer: 3000,
                    onAfterClose: setTimeout(function() {
                        location.reload();
                    }, 3000)
                });
            } else {
                Swal.fire({
                    position: 'center',
                    type: 'success',
                    title: 'Correct',
                    timer: 3000,
                    onAfterClose: setTimeout(function() {
                        location.reload();
                    }, 3000)
                });
            }

        }
    };
    xml.open("GET", serverURL + 'selectAnswer?id=' + id);
    xml.send();
}
