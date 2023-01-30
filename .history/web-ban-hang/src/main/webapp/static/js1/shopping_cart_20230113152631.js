$(document).ready(function () {
  $(".minusButton").on("click", function (evt) {
    evt.preventDefault();
    decreaseQuantity($(this));
  });
  $(".plusButton").on("click", function (evt) {
    quantity = parseInt($(".quantity-cart-item").attr("value")) + 1;
    $(".quantity-cart-item").text(quantity);
    evt.preventDefault();
    increaseQuantity($(this));
  });

  $(".link-remove").on("click", function (evt) {
    evt.preventDefault();
    removeFormCart($(this));
  });

  $("#transport").on("click", function (evt) {
    valueTransport = $(this).val();
    amount = $("#amount").attr("value");
    all = parseFloat(valueTransport) + parseFloat(amount);
    $("#amount").text(all);
  });

  $("#transport1").on("click", function (evt) {
    valueTransport = $(this).val();
    amount = $("#amount").attr("value");
    all = parseFloat(valueTransport) + parseFloat(amount);
    $("#amount").text(all);
  });

  updateTotal();
});

function removeFormCart(link) {
  url = link.attr("href");

  $.ajax({
    type: "POST",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfValue);
    },
  })
    .done(function (response) {
      alert("Xóa khỏi giỏ hàng thành công!");
      rowNumber = link.attr("rowNumberCart");
      removeProduct(rowNumber);
      updateTotal();
      $(".quantity-cart-item").text(response);
    })
    .fail(function () {});
}

function removeProduct() {
  rowId = "rows" + rowNumber;
  $("#" + rowId).remove();
}

function decreaseQuantity(link) {
  productId = link.attr("pid");
  qtyInput = $("#quantity" + productId);
  newQty = parseInt(qtyInput.val()) - 1;
  if (newQty > 0) {
    qtyInput.val(newQty);
    updateQuantity(productId, newQty);
  }
}

function increaseQuantity(link) {
  productId = link.attr("pid");
  console.log(productId);
  qtyInput = $("#quantity" + productId);
  newQty = parseInt(qtyInput.val()) + 1;

  if (newQty > 0) {
    qtyInput.val(newQty);
    updateQuantity(productId, newQty);
  }
}

function updateQuantity(productId, quantity) {
  url = "/cart/update/" + productId + "/" + quantity;

  $.ajax({
    type: "POST",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfValue);
    },
  })
    .done(function (newSubtotal) {
      updateSubtotal(newSubtotal, productId);
      updateTotal();
      $(".quantity-cart-item").text(response);
    })
    .fail(function () {});
}

function updateSubtotal(newSubtotal, productId) {
  newSubtotals = parseFloat(newSubtotal);
  newSubtotalss = newSubtotals.toLocaleString();
  $("#subtotal" + productId).text(newSubtotalss);
}

function updateTotal() {
  total = 0.0;

  $(".productSubtotal").each(function (index, element) {
    total += parseFloat(element.innerHTML);
  });
  // totals = total.toLocaleString();

  $("#totalAmount").text(total.toLocaleString(); + "đ");
}
