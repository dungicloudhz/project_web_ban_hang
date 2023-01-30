$(document).ready(function () {
  $("#buttonAdd2Cart").on("click", function (e) {
    add2Cart();
  });
});

function add2Cart() {
  productId = $("#buttonAdd2Cart").attr("pid");
  url = "/cart/add/" + productId + "/" + 1;

  $.ajax({
    type: "POST",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfValue);
    },
  })
    .done(function (response) {
      alert("Thêm vào giỏ hành thành công!");
      $(".quantity-cart-item").text(response);
    })
    .fail(function () {});
}
