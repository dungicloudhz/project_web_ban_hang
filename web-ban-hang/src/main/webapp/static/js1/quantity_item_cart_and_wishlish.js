$(document).ready(function () {
  getQuantityWishlish();
  getQuatityItemCart();
});

function getQuantityWishlish() {
  url = "/wishlish/cart";

  $.ajax({
    type: "GET",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfValue);
    },
  })
    .done(function (response) {
      $(".quantity-wishlish").text(response);
      $(".quantity-wishlish").attr("value", response);
    })
    .fail(function () {
      alert("Fail");
    });
}

function getQuatityItemCart() {
  url = "/quantity/cart";

  $.ajax({
    type: "GET",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfValue);
    },
  })
    .done(function (response) {
      $(".quantity-cart-item").attr("value", response);
      $(".quantity-cart-item").text(response);
    })
    .fail(function () {
      alert("Fail");
    });
}
