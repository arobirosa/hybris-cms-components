<%@ page trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:url value="/fragments/newsletter/signUp" var="newsletterSignUpUrl" />

<div class="newsletter-signup">
    <h1><c:out value="${title}" escapeXml="true" /></h1>
    <form name="newsletter_sign_up_form_${fn:escapeXml(component.uid)}" method="post" action="${fn:escapeXml(newsletterSignUpUrl)}">
        <div class="control-group">
            <label for="emailAddress">
                <spring:theme code="newsletter.signup.emailAddress.label" var="emailAddressPlaceholder" />
            </label>
            <ycommerce:testId code="newsletter_sign_up_form">
                <input type="email" id="emailAddress" name="emailAddress"
                       class="form-control" value=""
                       maxlength="100" />
                <input type="checkbox" id="agreed" name="agreed"
                       class="form-control" required="true">${agreementText}</>

            </ycommerce:testId>

            <span class="input-group-btn"> <ycommerce:testId code="newsletter_sign_up_button">
					<button class="btn btn-link" type="submit" id="newsletter_sign_up_button">
						<spring:theme code="newsletter.signup.submit.label"/>
					</button>
            </ycommerce:testId>
			</span>
        </div>
    </form>
</div>

