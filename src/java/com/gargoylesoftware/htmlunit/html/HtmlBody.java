/*
 * Copyright (c) 2002, 2004 Gargoyle Software Inc. All rights reserved.
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

import java.util.Map;

/**
 * Wrapper for the html element "body".
 *
 * @version  $Revision$
 * @author  <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @author  David K. Taylor
 * @author <a href="mailto:cse@dynabean.de">Christian Sell</a>
 */
public class HtmlBody extends ClickableElement {

    /** the HTML tag represented by this element */
    public static final String TAG_NAME = "body";

    /**
     * Create an instance of HtmlBody
     *
     * @param page The HtmlPage that contains this element.
     * @param attributes the initial attributes
     */
    public HtmlBody( final HtmlPage page, final Map attributes) {
        super(page, attributes);
    }

    /**
     * @return the HTML tag name
     */
    public String getTagName() {
        return TAG_NAME;
    }

    /**
     * Return the value of the attribute "onload".  Refer to the
     * <a href='http://www.w3.org/TR/html401/'>HTML 4.01</a>
     * documentation for details on the use of this attribute.
     *
     * @return The value of the attribute "onload"
     * or an empty string if that attribute isn't defined.
     */
    public final String getOnLoadAttribute() {
        return getAttributeValue("onload");
    }


    /**
     * Return the value of the attribute "onunload".  Refer to the
     * <a href='http://www.w3.org/TR/html401/'>HTML 4.01</a>
     * documentation for details on the use of this attribute.
     *
     * @return The value of the attribute "onunload"
     * or an empty string if that attribute isn't defined.
     */
    public final String getOnUnloadAttribute() {
        return getAttributeValue("onunload");
    }


    /**
     * Return the value of the attribute "background".  Refer to the
     * <a href='http://www.w3.org/TR/html401/'>HTML 4.01</a>
     * documentation for details on the use of this attribute.
     *
     * @return The value of the attribute "background"
     * or an empty string if that attribute isn't defined.
     */
    public final String getBackgroundAttribute() {
        return getAttributeValue("background");
    }


    /**
     * Return the value of the attribute "bgcolor".  Refer to the
     * <a href='http://www.w3.org/TR/html401/'>HTML 4.01</a>
     * documentation for details on the use of this attribute.
     *
     * @return The value of the attribute "bgcolor"
     * or an empty string if that attribute isn't defined.
     */
    public final String getBgcolorAttribute() {
        return getAttributeValue("bgcolor");
    }


    /**
     * Return the value of the attribute "text".  Refer to the
     * <a href='http://www.w3.org/TR/html401/'>HTML 4.01</a>
     * documentation for details on the use of this attribute.
     *
     * @return The value of the attribute "text"
     * or an empty string if that attribute isn't defined.
     */
    public final String getTextAttribute() {
        return getAttributeValue("text");
    }


    /**
     * Return the value of the attribute "link".  Refer to the
     * <a href='http://www.w3.org/TR/html401/'>HTML 4.01</a>
     * documentation for details on the use of this attribute.
     *
     * @return The value of the attribute "link"
     * or an empty string if that attribute isn't defined.
     */
    public final String getLinkAttribute() {
        return getAttributeValue("link");
    }


    /**
     * Return the value of the attribute "vlink".  Refer to the
     * <a href='http://www.w3.org/TR/html401/'>HTML 4.01</a>
     * documentation for details on the use of this attribute.
     *
     * @return The value of the attribute "vlink"
     * or an empty string if that attribute isn't defined.
     */
    public final String getVlinkAttribute() {
        return getAttributeValue("vlink");
    }


    /**
     * Return the value of the attribute "alink".  Refer to the
     * <a href='http://www.w3.org/TR/html401/'>HTML 4.01</a>
     * documentation for details on the use of this attribute.
     *
     * @return The value of the attribute "alink"
     * or an empty string if that attribute isn't defined.
     */
    public final String getAlinkAttribute() {
        return getAttributeValue("alink");
    }
}
