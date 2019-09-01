getOptions();

function getOptions() {
    let restURL = serverURL + "rest/question/all";
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (this.status === 200 && this.readyState === 4) {
            var myObj = JSON.parse(this.responseText);
            var options = '';

            for (var i = 0; i < myObj.length; i++)
                options += '<option value="' + myObj[i] + '" />';
            console.log(myObj[i]);
        }
        document.getElementById('categories').innerHTML = options;
    };
    xmlhttp.open("GET", restURL, true);
    xmlhttp.send();
}

document.addEventListener("submit", function (event) {
        event.preventDefault();
        if (window.confirm("Skutočne si želáte otázku zapísať do databázy?\nDo you really wish to upload the question?")) {

            let form = document.getElementById('formQ');

            let question = {
                content: form.elements['content'].value,
                category: form.elements["category"].value,
                answers: [
                    {
                        answer: form.elements['answerA'].value,
                        correct: form.elements['correctA'].checked
                    },
                    {
                        answer: form.elements['answerB'].value,
                        correct: form.elements['correctB'].checked
                    },
                    {
                        answer: form.elements['answerC'].value,
                        correct: form.elements['correctC'].checked
                    },
                    {
                        answer: form.elements['answerD'].value,
                        correct: form.elements['correctD'].checked
                    }
                ],
            };
            process(question);
        } else {

        }
    }
);

function process(values) {
    let request = new XMLHttpRequest();
    let url = serverURL + "rest/question";
    request.onreadystatechange = function () {
    };
    request.open("POST", url);
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify(values));
    window.location.href = serverURL + "form";
}

(function ($) {
    "use strict";

    /*==================================================================
    [ Validate after type ]*/
    $('.validate-input .input100').each(function () {
        $(this).on('blur', function () {
            if (validate(this) == false) {
                showValidate(this);
            } else {
                $(this).parent().addClass('true-validate');
            }
        })
    })

    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit', function () {
        var check = true;

        for (var i = 0; i < input.length; i++) {
            if (validate(input[i]) == false) {
                showValidate(input[i]);
                check = false;
            }
        }
        return check;
    });


    $('.validate-form .input100').each(function () {
        $(this).focus(function () {
            hideValidate(this);
            $(this).parent().removeClass('true-validate');
        });
    });

    function validate(input) {
        if ($(input).attr('name') == 'datalist') {
            var x = document.getElementById("categories").options.length;
            for (var i = 0; i < x; i++) {
                if ($(input).val().trim() == document.getElementById("categories").options.item(i).value) {
                    return true;
                }
            }
            return false;
        }
        if ($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if ($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        } else {
            if ($(input).val().trim() == '') {
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');

        $(thisAlert).append('<span class="btn-hide-validate">&#xf136;</span>')
        $('.btn-hide-validate').each(function () {
            $(this).on('click', function () {
                hideValidate(this);
            });
        });
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).removeClass('alert-validate');
        $(thisAlert).find('.btn-hide-validate').remove();
    }

})(jQuery);