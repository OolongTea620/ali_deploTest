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
    $.ajax({
        url:'/order/product',
        type:'POST',
        contentType:'application/json',
        data:JSON.stringify(productData),
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
    console.log(data);
}

function searchProducts(){
    let keyword=document.getElementById("search").value;
    let searchUrl =  ``;
    $.ajax({
        url:searchUrl,
        type:'GET',
        success:function(response){
            alert('keyword가 정상적으로 검색되었습니다');
            showProductData(response.success());
        },
        error:function(error){
            alert('검색 실패');
            console.log(error);
        }
    });
}
