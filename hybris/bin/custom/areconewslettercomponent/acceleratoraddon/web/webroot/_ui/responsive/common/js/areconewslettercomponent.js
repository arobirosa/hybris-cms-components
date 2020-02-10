ACC.areconewslettercomponent = {

    _autoload: [
        "bindSignUpForms"
    ],

    bindSignUpForms: function() {
        $('.js-newsletter-component-submit').on('click', function(event) {
            event.preventDefault();
            var form = $(this).parent;
            var formData = $(form).serialize();

            $.ajax({
                url: form.action,
                type: 'POST',
                data: formData,
                dataType: "html",
                success: function (data, textStatus) {
                    // Update the form container with the received html
                    $(form).parent.html(data);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    /* TODO Localize this error message. */
                    $(form).parent.html("There was an error while signing you to the newsletter.");
                }
            });


        });
    }
};
