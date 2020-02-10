<%@ page trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:url value="/newsletter/signUp" var="newsletterSignUpUrl" />
<div class="newsletter-section">
    <div class="row">
        <div class="col-md-10  col-md-offset-1">
            <h1><c:out value="${component.title}" escapeXml="true" /></h1>
            <form id="newsletter_sign_up_form_${fn:escapeXml(component.uid)}" method="post" action="${fn:escapeXml(newsletterSignUpUrl)}" class="form-inline">
                <ycommerce:testId code="newsletter_sign_up_form">
                    <div class="form-group">
                        <spring:theme code="newsletter.signup.emailAddress.label" var="emailAddressPlaceholder"/>
                        <input type="email" id="emailAddress" name="emailAddress"  class="form-control" value="" size="50" maxlength="100"
                               placeholder="${fn:escapeXml(emailAddressPlaceholder)}"/>
                    </div>
                    <div class="form-group">
                        <div class="checkbox">
                            <label class="control-label uncased newsletter-agreement-label">
                                <input type="checkbox" id="agreed" name="agreed"  class="form-control newsletter-agreement-input" required="true">${component.agreementText}</input>
                            </label>
                        </div>
                    </div>
                    <button class="btn btn-primary pull-right" type="submit" id="newsletter_sign_up_button">
                        <spring:theme code="newsletter.signup.submit.label"/>
                    </button>
                </ycommerce:testId>
            </form>
        </div>
    </div>

</div>
