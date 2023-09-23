function openOrderModal(){
    selectedProductId=null;
    document.getElementById('modalTitle').innerText='제품 주문';
    document.getElementById('addButton').style.display='block';
    document.getElementById('productModal').style.display='block';
}

function closeModal(){
    document.getElementById('productModal').style.display='none';
}

$('.orderFormOpen').on('click', function() {
    openOrderModal();
    // 클릭한 열의 데이터 가져오기
    const thisRow = $(this).closest('tr');
    const td = thisRow.children();
    const id= td.eq(0).text();
    const price = td.eq(3).text();
    const stock = td.eq(4).text();
    console.log(id, price, stock);

    $('#productId').val(id);
    $('#productPrice').text(price);
    $('#stock').text(stock);
})

function  requestOrder(productData) {
    var Access_Token = localStorage.getItem("Access_Token");
    var Refresh_Token = localStorage.getItem("Refresh_Token");

    $.ajax({
        url:'http://localhost:8080/api/order/product',
        type:'POST',
        contentType:'application/json',
        data:JSON.stringify(productData),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-type","application/json");
            xhr.setRequestHeader("Access_Token", Access_Token);
            xhr.setRequestHeader("Refresh_Token", Refresh_Token);
        },
        success:function(response){
            alert('상품이 성공적으로 구입되었습니다.');
        },
        error:function(error){
            alert('상품구매에 실패했습니다.');
        }
    });
}
function orderProducts(){
    var stock = $('#stock').text();
    var qnt = $('#qntity').val();

    if (Number(stock) < qnt ) {
        alert("재고가 부족합니다");
    } else if (Number(qnt) === 0) {
        alert("재고를 입력해 주세요");
    } else {
        const productData={
            productId: $('#productId').val(),
            qnt: qnt
        };
        requestOrder(productData);
    }
}

function showProductData(data) {
}

function searchProducts(){
    //let keyword = document.getElementById("search").value;
    let searchUrl =  'http://localhost:8080/api/products';
    $.ajax({
        url:searchUrl,
        type:'GET',
        success:function(response){
            console.log(response);
            showProductData(response);
        },
        error:function(error){
            console.log(error);
            alert('조회 실패');
        }
    });
}

searchProducts();
