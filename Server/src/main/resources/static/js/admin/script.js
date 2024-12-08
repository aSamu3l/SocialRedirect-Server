var characters = 'abcdefghijklmnopqrstuvwxyz0123456789';

const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

function createAlias() {
    var alias = document.getElementById("alias").value;
    var dest = document.getElementById("destination").value;

    //Check if the fields are empty
    if (alias === "" && dest === "") {
        alert("Fill the ALIAS and DESTINATION fields");
        return;
    } else if (alias === "") {
        alert("Fill the ALIAS field");
        return;
    } else if (dest === "") {
        alert("Fill the DESTINATION field");
        return;
    }

    //Check if the URL is valid
    if (!isValidUrl(dest)) {
        alert("DESTINATION is not a valid URL");
        return;
    }

    for (let i = 0; i < alias.length; i++) {
        if (!characters.includes(alias[i])) {
            alert("Alias can only contain letters and numbers");
            return;
        }
    }

    var jj = JSON.stringify({
        "alias": alias,
        "destination": dest
    })

    $.ajax({
        url: "/admin/api/aliases",
        type: "POST",
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        data: jj,
        success: function (response) {
            window.location.reload(true);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("AJAX error: " + textStatus + ' : ' + errorThrown);
            alert("Error creating alias");
        }
    });
}

function deleteFunction(id) {
    document.getElementById("row-" + id).classList.add("deleting");
    setTimeout(function() {
        if (confirm("Delete Alias with ID: " + id)) {
            $.ajax({
                url: "/admin/api/aliases/" + id,
                type: "DELETE",
                headers: {
                    [csrfHeader]: csrfToken
                },
                success: function (response) {
                    window.location.reload(true);
                },
                error: function () {
                    alert("Impossible to delete the alias");
                }
            });
        }
        document.getElementById("row-" + id).classList.remove("deleting");
    }, 100);
}

function isValidUrl(str) {
    const pattern = new RegExp(
        '^([a-zA-Z]+:\\/\\/)' + // protocol
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR IP (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
        '(\\#[-a-z\\d_]*)?$', // fragment locator
        'i'
    );
    return pattern.test(str);
}