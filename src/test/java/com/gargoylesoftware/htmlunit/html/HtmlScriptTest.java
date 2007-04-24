/*
 * Copyright (c) 2002-2006 Gargoyle Software Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. The end-user documentation included with the redistribution, if any, must
 *    include the following acknowledgment:
 *
 *       "This product includes software developed by Gargoyle Software Inc.
 *        (http://www.GargoyleSoftware.com/)."
 *
 *    Alternately, this acknowledgment may appear in the software itself, if
 *    and wherever such third-party acknowledgments normally appear.
 * 4. The name "Gargoyle Software" must not be used to endorse or promote
 *    products derived from this software without prior written permission.
 *    For written permission, please contact info@GargoyleSoftware.com.
 * 5. Products derived from this software may not be called "HtmlUnit", nor may
 *    "HtmlUnit" appear in their name, without prior written permission of
 *    Gargoyle Software Inc.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GARGOYLE
 * SOFTWARE INC. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gargoylesoftware.htmlunit.html;

import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.MockWebConnection;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebTestCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for {@link HtmlScript}.
 * 
 * @version $Revision$
 * @author Marc Guillemot
 */
public class HtmlScriptTest extends WebTestCase {

    /**
     * Create an instance
     * 
     * @param name
     *            Name of the test
     */
    public HtmlScriptTest(final String name) {
        super(name);
    }

    /**
     * @throws Exception If an error occurs.
     */
    public void testBadExternalScriptReference() throws Exception {

        final String html = "<html><head><title>foo</title>"
                + "<script src='notExisting.js'></script>"
                + "</head><body></body></html>";

        final WebClient client = new WebClient();

        final MockWebConnection webConnection = new MockWebConnection(client);
        webConnection.setDefaultResponse("", 404, "not found", "text/html");
        webConnection.setResponse(URL_FIRST, html);
        client.setWebConnection(webConnection);

        try {
            client.getPage(URL_FIRST);
            fail("Should throw");
        }
        catch (final ScriptException e) {
            assertTrue("exception contains url of failing script", e
                    .getMessage().indexOf(URL_FIRST.toExternalForm()) > -1);

            assertNotNull(e.getCause());
            assertEquals(FailingHttpStatusCodeException.class, e.getCause()
                    .getClass());
            final FailingHttpStatusCodeException cause = (FailingHttpStatusCodeException) e
                    .getCause();
            assertEquals(404, cause.getStatusCode());
            assertEquals("not found", cause.getStatusMessage());
        }
    }

    /**
     * @throws Exception If an error occurs.
     */
    public void testAsText() throws Exception {
        final String htmlContent
            = "<html><head><title>foo</title></head><body>"
            + "<script id='script1'>"
            + "    var foo = 132;"
            + "</script></body></html>";

        final HtmlPage page = loadPage(htmlContent);

        final HtmlScript script = (HtmlScript) page.getHtmlElementById("script1");
        assertEquals("", script.asText());
    }

    /**
     * Verifies that the weird script src attribute used by the jQuery JavaScript library is
     * ignored silently (bug 1695279).
     * @throws Exception If the test fails.
     */
    public void testInvalidJQuerySrcAttribute() throws Exception {
        loadPage("<html><body><script src='//:'></script></body></html>");
    }

    /**
     * Verifies that if a script element executes "window.location.href=someotherpage", then subsequent
     * script tags, and any onload handler for the original page do not run.
     */
   public void testChangingLocationSkipsFurtherScriptsOnPage() throws Exception {

       final String firstPage
           = "<html><head></head>"
           + "<body onload='alert(\"body onload executing but should be skipped\")'>"
           + "<script>alert('First script executes')</script>"
           + "<script>window.location.href='" + URL_SECOND + "'</script>"
           + "<script>alert('Third script executing but should be skipped')</script>"
           +"</body></html>";

       final String secondPage
           = "<html><head></head><body>"
           + "<script>alert('Second page loading')</script>"
           + "</body></html>";

        final WebClient client = new WebClient();

        final MockWebConnection webConnection = new MockWebConnection(client);
        webConnection.setResponse(URL_FIRST, firstPage);
        webConnection.setResponse(URL_SECOND, secondPage);
        client.setWebConnection(webConnection);

        final List collectedAlerts = new ArrayList();
        client.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        client.getPage(URL_FIRST);
        final List expectedAlerts = Arrays.asList(new String[] { "First script executes", "Second page loading" });
        assertEquals(expectedAlerts, collectedAlerts);
    }

}
