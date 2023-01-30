$(document).ready(function () {
  $("#buttonAdd2CartByQuantity").on("click", function (e) {
    addToCart();
  });
});

function addToCart() {
  productId = $("#buttonAdd2CartByQuantity").attr("pid");
  quantity = $("#quantity" + productId).val();
  url = "/cart/add/" + productId + "/" + quantity;

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
    .fail(function () {
      alert("Fail");
    });
}

// $(document).ready(function () {
//   var addToCart = document.querySelectorAll("#buttonAdd3Cart");
//   $(addToCart).addClass("add-to-carts");
//   console.log($(".add-to-carts"));
//   $(".add-to-carts").on("click", function () {
//     productId = this.getAttribute("pid");
//     console.log(productId);
//     // addToCart(productId);
//     url = "/cart/add/" + productId + "/" + 1;

//     $.ajax({
//       type: "POST",
//       url: url,
//       beforeSend: function (xhr) {
//         xhr.setRequestHeader(csrfHeaderName, csrfValue);
//       },
//     })
//       .done(function (response) {})
//       .fail(function () {});
//   });
// });
