function positionBand() {
    band = document.getElementById('band');
    header = document.getElementById('header');
    left = document.getElementById('left');
    if (window.pageYOffset > header.clientHeight - band.clientHeight - 2) {
        band.style.position = "fixed";
        header.style.paddingBottom = band.clientHeight + 2 + "px";
        band.style.width = document.body.clientWidth - 4 + "px";
        if (document.documentElement.clientWidth > document.body.clientWidth)
            band.style.left = (document.documentElement.clientWidth - document.body.clientWidth) / 2 + "px";
        else
            band.style.left = -(document.documentElement.scrollLeft + document.body.scrollLeft) + "px";
        band.style.marginLeft = "2px";
        if (left.offsetWidth !== 260) {
            left.style.position = "fixed";
            left.style.width = document.body.clientWidth - 4 + "px";
            left.style.top = "32px";
            header.style.marginBottom = left.clientHeight + 2 + "px";
            if (document.documentElement.clientWidth > document.body.clientWidth)
                left.style.left = (document.documentElement.clientWidth - document.body.clientWidth) / 2 + "px";
            else
                left.style.left = -(document.documentElement.scrollLeft + document.body.scrollLeft) + 2 + "px";
        }
    } else {
        band.style.position = "";
        header.style.paddingBottom = "";
        band.style.width = "";
        band.style.left = "";
        band.style.marginLeft = "";
        left.style.position = "";
        left.style.width = "";
        left.style.top = "";
        header.style.marginBottom = "";
    }
}
window.onscroll = positionBand;
window.onresize = positionBand;

var rows = 1;

function addCarac() {
    var table = document.getElementById("caracs");
    rows++;
    table.insertRow(table.rows.length - 1).innerHTML += table.rows[1].innerHTML.replace(/_[1-9]+/g, "_" + rows);
    table.parentNode.scrollTop = 100000000;
}

function delCarac(t) {
    var table = document.getElementById("caracs");
    if (table.rows.length > 3)
        document.getElementById("caracs").deleteRow(t.parentNode.parentNode.rowIndex);
    else
        t.parentNode.parentNode.cells[1].firstChild.value = "";
}

/*
 * Teste si un input est valide. Doit aussi être testé côté serveur.
 * @param String input : id de l'input à vérifier
 * @param RegExp|String test : expression régulière ou chaine à comparer à la valeur de l'input
 * @param String msg : message à afficher au survol de l'input s'il n'est pas correct
 * @returns Boolean : true si le contenu de l'input est correct
 */
function check(input, test, msg) {
    var tag = document.getElementById(input);
    if (tag.originalTitle === undefined)
        tag.originalTitle = tag.title;
    if (tag.value === test || (test.constructor === RegExp && tag.value.match(test) !== null)) {
        tag.style.borderColor = "";
        tag.title = tag.originalTitle;
        return true;
    } else {
        tag.style.borderColor = "red";
        if (tag.originalTitle === "")
            tag.title = msg;
        else
            tag.title = tag.originalTitle + " - " + msg;                
        return false;
    }
}

function uncheck(input) {
    var tag = document.getElementById(input);
    tag.style.borderColor = "";
    tag.title = tag.originalTitle;
}

function checkRegister() {
    var ok = 1; // ce n'est pas un booléen pour éviter le 'court circuit' sur le AND
    ok &= check("username", /.../, "L'identifiant doit contenir au moins 3 charactères.");
    ok &= check("password", /.../, "Le mot de passe doit faire 3 charactères minimum");
    ok &= check("confirmPass", document.getElementById("password").value, "Le mot de passe saisi est différent");
    ok &= check("name", /./, "Champ requis");
    ok &= check("surname", /./, "Champ requis");
    ok &= check("address", /./, "Champ requis");
    ok &= check("mail", /.@./, "Adresse e-mail invalide");
    return ok === 1;
}

function checkSearch() {
    var ok = 1;
    ok &= check("minPrice", /^([0-9]+(\.[0-9]+)?)?$/, "Le prix doit être au format 123.45");
    ok &= check("maxPrice", /^([0-9]+(\.[0-9]+)?)?$/, "Le prix doit être au format 123.45");
    return ok === 1;
}

function checkAccount() {
    var ok = 1;
    ok &= check("username", /.../, "L'identifiant doit contenir au moins 3 charactères.");
    ok &= check("name", /./, "Champ requis");
    ok &= check("surname", /./, "Champ requis");
    ok &= check("addressPayement", /./, "Champ requis");
    ok &= check("addressDelivery", /./, "Champ requis");
    ok &= check("mail", /.@./, "Adresse e-mail invalide");
    return ok === 1;    
}

function checkConfirmAddress() {
    var ok = 1;
    ok &= check("nameFact", /./, "Champ requis");
    ok &= check("surnameFact", /./, "Champ requis");
    ok &= check("addressFact", /./, "Champ requis");
    if (! document.getElementById("sameAddr").checked) {
        ok &= check("nameLivr", /./, "Champ requis");
        ok &= check("surnameLivr", /./, "Champ requis");
        ok &= check("addressLivr", /./, "Champ requis");
    } else {
        uncheck("nameLivr");
        uncheck("surnameLivr");
        uncheck("addressLivr");
    }
    ok &= check("input_mail", /.@./, "Adresse e-mail invalide");
    ok &= check("input_tel", /.../, "Numéro de téléhpone invalide");
    return ok === 1;    
}

function sameAddrOnClick() {
    if (document.getElementById("sameAddr").checked) {
        document.getElementById("nameLivr").disabled = true;
        document.getElementById("surnameLivr").disabled = true;
        document.getElementById("addressLivr").disabled = true;
        uncheck("nameLivr");
        uncheck("surnameLivr");
        uncheck("addressLivr");
    } else {
        document.getElementById("nameLivr").disabled = false;
        document.getElementById("surnameLivr").disabled = false;
        document.getElementById("addressLivr").disabled = false;
    }
}