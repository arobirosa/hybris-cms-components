ACC.areconewslettercomponent = {

    _autoload: [
        "bindSignUpForms",
        "handleConditionsChange"
    ],

    bindSignUpForms: function() {
        $('.js-newsletter-component-form').submit(function(event) {
            event.preventDefault();
            var form = $(this);
            var formData = $(form).serialize();
            var formAction = form[0].action;

            $.ajax({
                url: formAction,
                type: 'POST',
                data: formData,
                dataType: "html",
                success: function (data, textStatus) {
                    // Update the form container with the received html
                    $(form).parent().html(data);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    /* TODO Localize this error message. */
                    $(form).parent().html("<div class='error'>There was an error while signing you to the newsletter.</div>");
                }
            });
        });
    },
    handleConditionsChange: function() {
        $(".js-newsletter-component-input-agreed").change( function(e) {
            e.preventDefault();
            var form = $(this).parents('form:first');
            var btnSubmit = form.find(':submit');

            if( $(this).is(':checked') )
            {
                btnSubmit.prop('disabled',false);
            }
            else
            {
                btnSubmit.prop('disabled',true);
            }
        });
    }
};
