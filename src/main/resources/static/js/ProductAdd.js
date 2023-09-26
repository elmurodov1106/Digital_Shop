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


// function showInputs() {
//     const selection = document.getElementById("productType").value;
//     const form = document.getElementById("dynamic-form");
//
//     // Remove existing inputs
//     while (form.firstChild) {
//         form.removeChild(form.firstChild);
//     }
//
//     if (selection === "Elektronik") {
//         form.innerHTML += `
//             <label for="Elektronik" class="form-label">Enter Elektronik:</label>
//             <input type="text" id="Elektronik" name="Elektronik" class="form-control"><br>
//             <label for="price" class="form-label">Enter Price:</label>
//             <input type="text" id="price" name="price" class="form-control"><br>
//         `;
//     } else if (selection === "Laptop") {
//         form.innerHTML += `
//             <label for="name" class="form-label">Enter Colour:</label>
//             <input type="text" id="name" name="colour" class="form-control"><br>
//             <label for="type" class="form-label">Enter Weight:</label>
//             <input type="number" id="type" name="weight" class="form-control"><br>
//             <label for="region" class="form-label">Enter Memory:</label>
//             <input type="number" id="region" name="memory" class="form-control"><br>
//              <label for="region" class="form-label">Enter Ram:</label>
//             <input type="number" id="region" name="Ram" class="form-control"><br>
//              <label for="region" class="form-label">Enter ScreenSize:</label>
//             <input type="number" id="region" name="ScreenSize" class="form-control"><br>
//              <label for="region" class="form-label">Enter Ghz:</label>
//             <input type="text" id="region" name="Ghz" class="form-control"><br>
//         `;
//     } else if (["Phone", "Tv", "Product"].includes(selection)) {
//         form.innerHTML += `
//             <label for="name" class="form-label">Enter Name:</label>
//             <input type="text" id="name" name="name" class="form-control"><br>
//         `;
//     }
// }
//
//
// showInputs();