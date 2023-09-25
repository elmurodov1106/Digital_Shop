const loginbutton = document.getElementById('loginbutton');
const loginerror = document.getElementById('invalidlogin');
const username = document.getElementById('Username');
const password = document.getElementById('password');
const itemid = document.getElementById('UrunID');
const itemname = document.getElementById('UrunAdi');
const quantity = document.getElementById('UrunMiktar');
const category = document.getElementById('Kategori');
const pushbutton = document.getElementById('ekleButton');
var items = [];

class item {
    id;
    itemname;
    quantity;
    category;
}

pushbutton.addEventListener('click' , function() {
    var dynamicitem = new item();
    dynamicitem.id = itemid.value;
    dynamicitem.itemname = itemname.value;
    dynamicitem.quantity = quantity.value;
    dynamicitem.category = category.value;
    items.push(dynamicitem);
    localStorage.setItem('items' , JSON.stringify(items));
    console.log(items[0].itemname);
    itemid.textContent = '';
    itemname.textContent = '';
    quantity.textContent = '';
    category.textContent = '';

})