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
}