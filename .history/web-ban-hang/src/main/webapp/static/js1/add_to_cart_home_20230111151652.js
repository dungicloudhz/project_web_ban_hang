$(document).ready(function () {
  // $("#buttonAdd2Cart").on("click", function (e) {
  //   addToCart();
  // });
  var addToCart = document.querySelectorAll("#buttonAdd3Cart");
  $(addToCart).addClass("add-to-carts");
  console.log($(".add-to-carts"));
  $(".add-to-carts").on("click", function () {
    productId = this.getAttribute("pid");
    console.log(productId);
    // addToCart(productId);
    url = "/cart/add/" + productId + "/" + 1;

    $.ajax({
      type: "POST",
      url: url,
      beforeSend: function (xhr) {
        xhr.setRequestHeader(csrfHeaderName, csrfValue);
      },
    })
      .done(function (response) {
        alert("Thêm vào giỏ hàng thành công");
        $(".quantity-cart-item").text(response);
      })
      .fail(function () {});
  });
});
