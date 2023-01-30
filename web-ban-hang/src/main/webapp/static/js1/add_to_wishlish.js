$(document).ready(function () {
  var addToCart = document.querySelectorAll("#buttonAdd2Wishlish");
  $(addToCart).addClass("add-to-wishlishes");
  console.log($(".add-to-wishlishes"));
  $(".add-to-wishlishes").on("click", function () {
    productId = this.getAttribute("pid");
    console.log(productId);
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
      .fail(function () {});
  });
});
