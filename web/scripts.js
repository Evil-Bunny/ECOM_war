function positionBand() {
        band = document.getElementById('band');
        header = document.getElementById('header');
        left = document.getElementById('left');
        if (window.pageYOffset > header.clientHeight - band.clientHeight -2) {
                band.style.position = "fixed";
                header.style.paddingBottom = band.clientHeight+2 + "px";
                band.style.width = document.body.clientWidth-4 + "px";
                if (document.documentElement.clientWidth > document.body.clientWidth) 
                        band.style.left = (document.documentElement.clientWidth - document.body.clientWidth)/2 + "px";
                else
                        band.style.left = -(document.documentElement.scrollLeft+document.body.scrollLeft) + "px";
                band.style.marginLeft = "2px";
                if (left.offsetWidth !== 260) {
                        left.style.position = "fixed";
                        left.style.width = document.body.clientWidth-4 + "px";
                        left.style.top = "32px";
                        header.style.marginBottom = left.clientHeight+2 + "px";
                        if (document.documentElement.clientWidth > document.body.clientWidth) 
                                left.style.left = (document.documentElement.clientWidth - document.body.clientWidth)/2 + "px";
                        else
                                left.style.left = -(document.documentElement.scrollLeft+document.body.scrollLeft)+2 + "px";
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
    table.insertRow(table.rows.length-1).innerHTML += table.rows[1].innerHTML.replace(/_[1-9]+/g, "_"+rows);
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
 * @param RegExp regex : expression régulière permettant de vérifier le champ
 * @param String msg : message à afficher au survol de l'input s'il n'est pas correct
 * @returns Boolean : true si le contenu de l'input est correct
 */
function check(input, regex, msg) {
    var tag = document.getElementById(input);
    //alert(tag.value.match(regex).prototype);
    if (tag.value.match(regex) !== null) {
        return true;
    } else {
        tag.style.borderColor = "red";
        if (msg !== undefined)
            tag.title = msg;
        return false;
    }
}

function checkRegister() {
    var ok = 0;
    ok &= check("username", /.../, "L'identifiant doit contenir au moins 3 charactères.");
    ok &= check("password", /.../, "Le mot de passe doit faire 3 charactères minimum");
    ok &= check("name", /./, "Champ requis");
    ok &= check("surname", /./, "Champ requis");
    ok &= check("address", /./, "Champ requis");
    return ok === 1;
}