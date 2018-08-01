//设置超时时间 等待响应
// system = require('system')
// address = system.args[1];
// var page = require('webpage').create();
// var url = address;
// page.open(url, function (status) {
//     //Page is loaded!
//     if (status !== 'success') {
//         console.log('Unable to post!');
//     } else {
//         window.setTimeout(function () {
//             page.render("test1.png");  //截图
//             console.log(page.content);
//             phantom.exit();
//         }, 2000);
//     }
// });


system = require('system')
address = system.args[1];
var page = require('webpage').create();
var url = address;
page.open(url, function (status) {
    //Page is loaded!
    if (status !== 'success') {
        console.log('Unable to post!');
    } else {
        console.log(page.content);
        phantom.exit();
    }
});