$(document).ready(function () {
  $(".link-remove-wishlish").on("click", function (evt) {
    evt.preventDefault();
    console.log($(this).attr("href"));
    removeFormWish($(this));
  });
});

function removeFormWish(link) {
  url = link.attr("href");

  $.ajax({
    type: "POST",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfValue);
    },
  })
    .done(function (response) {
      alert("Xóa thành công!");
      rowNumber = link.attr("rowNumber");
      removeProductInWishlish(rowNumber);
      $(".quantity-wishlish").text(response);
    })
    .fail(function () {});
}

function removeProductInWishlish() {
  rowId = "row" + rowNumber;
  $("#" + rowId).remove();
}
