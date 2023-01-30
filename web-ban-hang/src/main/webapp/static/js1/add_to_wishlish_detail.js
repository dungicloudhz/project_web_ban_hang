$(document).ready(function () {
  $("#buttonAdd3Wishlish").on("click", function (e) {
    addToWishlish();
  });
});

function addToWishlish() {
  // quantity = $("#quantity" + productId).val();
  productId = $("#buttonAdd3Wishlish").attr("pid");
  url = "/wishlish/add/" + productId;

  $.ajax({
    type: "POST",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfValue);
    },
  })
    .done(function (response) {
      alert("Thêm vào yêu thích thành công");
      $(".quantity-wishlish").text(response);
    })
    .fail(function () {
      alert("Fail");
    });
}
