/*
 * Copyright (c) 2005 Gargoyle Software Inc. All rights reserved.
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
package com.gargoylesoftware.htmlunit.javascript;

import com.gargoylesoftware.htmlunit.MockWebConnection;
import com.gargoylesoftware.htmlunit.SubmitMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebTestCase;
import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;



/**
 * Multi-threaded JavaScript engine test.
 *
 * @author David D. Kilzer
 * @version $Revision$
 */
public class ThreadTest extends WebTestCase {

    /**
     * Create an instance
     *
     * @param name The name of the test
     */
    public ThreadTest(String name) {
        super(name);
    }


    /**
     * @throws InterruptedException if the test fails
     */
    public void testJavaScriptEngineInMultipleThreads() throws InterruptedException {

        TestThread thread1 = new TestThread("thread1");
        TestThread thread2 = new TestThread("thread2");
        TestThread thread3 = new TestThread("thread3");
        TestThread thread4 = new TestThread("thread4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        assertTrue("thread1 not successful", thread1.isSuccessful());
        assertTrue("thread2 not successful", thread2.isSuccessful());
        assertTrue("thread3 not successful", thread3.isSuccessful());
        assertTrue("thread4 not successful", thread4.isSuccessful());
    }



    private static class TestThread extends Thread {

        private boolean successful_ = false;


        public TestThread(String name) {
            super(name);
        }


        public void run() {
            try {
                testCallInheritedFunction();
                successful_ = true;
            }
            catch (Exception e) {
                System.err.println(">>> Thread " + getName());
                e.printStackTrace(System.err);
                successful_ = false;
            }
        }


        public boolean isSuccessful() {
            return successful_;
        }


        /**
         * @see SimpleScriptableTest#testCallInheritedFunction()
         */
        public void testCallInheritedFunction() throws Exception {

            final WebClient client = new WebClient();
            final MockWebConnection webConnection = new MockWebConnection(client);
            final String content
                    = "<html><head><title>foo</title><script>"
                      + "function doTest() {\n"
                      + "    document.form1.textfield1.focus();\n"
                      + "    alert('past focus');\n"
                      + "}\n"
                      + "</script></head><body onload='doTest()'>"
                      + "<p>hello world</p>"
                      + "<form name='form1'>"
                      + "    <input type='text' name='textfield1' id='textfield1' value='foo' />"
                      + "</form>"
                      + "</body></html>";

            webConnection.setDefaultResponse(content);
            client.setWebConnection(webConnection);

            final List expectedAlerts = Collections.singletonList("past focus");
            final List collectedAlerts = new ArrayList();
            client.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

            final HtmlPage page = (HtmlPage) client.getPage(
                    URL_GARGOYLE, SubmitMethod.POST, Collections.EMPTY_LIST);
            assertEquals("foo", page.getTitleText());
            assertEquals("focus not changed to textfield1",
                         page.getFormByName("form1").getInputByName("textfield1"),
                         page.getWebClient().getElementWithFocus());
            assertEquals(expectedAlerts, collectedAlerts);
        }
    }

}
