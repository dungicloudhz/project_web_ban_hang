$(document).ready(function () {
  var modalDetail = document.querySelectorAll("#modal-detail");
  $(modalDetail).addClass("model-detail-item");
  console.log($(".model-detail-item"));
  $(".model-detail-item").on("click", function (event) {
    event.preventDefault();
    // alert("Thêm vào giỏ hàng thành công");
    url = $(this).attr("href");
    // url = "/view/modal";

    $.ajax({
      type: "GET",
      url: url,
      beforeSend: function (xhr) {
        xhr.setRequestHeader(csrfHeaderName, csrfValue);
      },
    })
      .done(function (response) {
        $(".img-pro-detail").attr("src", "/static/images/" + response.image);

        $(".link-add-to-cart").attr("pid", response.productDetailId);
        $(".set-quantity").attr("id", "quantity" + response.productDetailId);
        $(".add-wishlish").attr("pid", response.productDetailId);
        $(".product-detail-name").text(response.productDetailName);
        unitPrices = response.unitPrice.toLocaleString();
        $(".old-price").text(unitPrices + "đ");
        currentPrice = parseFloat(
          response.unitPrice - (response.unitPrice * response.discount) / 100
        ).toLocaleString();

        $(".cuttent-price").text(currentPrice + "đ");
        $(".discount-flag").text("Sale " + response.discount + "%");
        $(".quickview-para").text(response.description);
        $(".color-detail").text(response.colorName);
        $(".memory-storage").text(response.memoryStorageName);
        $(".ram").text(response.sizeRam);
        $("#exampleModal").modal("show");
      })
      .fail(function () {
        alert("FAIL");
      });
  });
});

// function showModal(link) {
//   url = link;

//   $.get(url, function (responseJSon) {})
//     .done(function () {
//       alert("Done");
//     })
//     .fail(function () {
//       alert("Failed");
//     });
// }
