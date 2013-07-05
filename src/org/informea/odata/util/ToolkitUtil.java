/* Copyright 2011 UNEP (http://www.unep.org)
 * This file is part of InforMEA Toolkit project.
 * InforMEA Toolkit is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * InforMEA Toolkit is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * InforMEA Toolkit. If not, see http://www.gnu.org/licenses/.
 */
package org.informea.odata.util;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.informea.odata.constants.MimeType;
import org.informea.odata.pojo.AbstractContact;
import org.informea.odata.pojo.AbstractCountryProfile;
import org.informea.odata.pojo.AbstractCountryReport;
import org.informea.odata.pojo.AbstractDecision;
import org.informea.odata.pojo.AbstractMeeting;
import org.informea.odata.pojo.AbstractNationalPlan;
import org.informea.odata.pojo.AbstractSite;
import org.informea.odata.producer.InvalidValueException;
import org.informea.odata.producer.toolkit.Configuration;
import org.informea.odata.producer.toolkit.IDataProvider;
import org.informea.odata.producer.toolkit.Producer;
import org.informea.odata.producer.toolkit.impl.DatabaseDataProvider;


/**
 * Various utility methods for toolkit
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
public class ToolkitUtil {

    private static final Logger log = Logger.getLogger(ToolkitUtil.class.getName());

    /**
     * Read decision document - file from disk and return its content
     * @param decision_id ID of the decision (used for friendly logging errors)
     * @param mime Document mime type. Used to to determine if file is binary or text
     * @param path Path to the document
     * @return Document content
     * @throws InvalidValueException If file does not exists or other error occurr
     */
    public static byte[] readDecisionDocument(String decision_id, MimeType mime, String path) throws InvalidValueException {
        byte[] ret = null;

        File f = new File(path);
        if (!f.exists()) {
            throw new InvalidValueException(String.format("'documents' is invalid (if exists, document->disk_path must point to a valid, existing file. Check informea_decisions_documents and path on disk: %s) (Affected decision with ID:%s)", path, decision_id));
        }
        if (!f.canRead()) {
            throw new InvalidValueException(String.format("'documents' is invalid (document->disk_path is valid and pointing to a file that access is denied. Check informea_decisions_documents and path on disk: %s) (Affected decision with ID:%s)", path, decision_id));
        }

        if (mime == MimeType.PDF || mime == MimeType.DOC || mime == MimeType.RTF || mime == MimeType.ODT) {
            try {
                ret = readBinaryFile(f);
            } catch (FileNotFoundException ex) {
                throw new InvalidValueException(String.format("Cannot read decision (id: %s), missing document from disk: %s", decision_id, path), ex);
            } catch (IOException ioex) {
                throw new InvalidValueException(String.format("Error reading decision (id: %s) document from disk: %s", decision_id, path), ioex);
            }
        } else {
            // Text file
            try {
                ret = readTextFile(f, Charset.defaultCharset()); // TODO: Variable charset
            } catch (FileNotFoundException ex) {
                throw new InvalidValueException(String.format("Cannot read decision (id: %s), missing document from disk: %s", decision_id, path), ex);
            } catch (IOException ioex) {
                throw new InvalidValueException(String.format("Error reading decision (id: %s) document from disk: %s", decision_id, path), ioex);
            }
        }

        return ret;
    }


    /**
     * Read decision document - file located at an specified URL
     * @param decision_id ID of the decision (used for friendly logging errors)
     * @param mime Document mime type. Used to to determine if file is binary or text
     * @param url Valid URL to the document
     * @return Document content
     * @throws InvalidValueException If file does not exists or other error occurr
     */
    public static byte[] downloadDecisionDocument(String decision_id, MimeType mime, String url) throws InvalidValueException {
        byte[] ret = null;
        BufferedInputStream is = null;
        URLConnection uc = null;
        ByteArrayOutputStream bb = null;
        try {
            URL the_url = new URL(url);
            uc = the_url.openConnection();
            // TODO: cristiroma - Should check uc.getResponseCode() that returns 200? (May return 301 or 302 though and still be valid).
            // Still problems may encounter if header is 200, but CMS returns an 'Not found page' - see DotNetNuke etc.
            is = new BufferedInputStream(uc.getInputStream());
            bb = new ByteArrayOutputStream(uc.getContentLength());
            log.info(String.format("File details: Content-Type: %s, Content-Length: %s bytes, Content-Encoding: %s",
                    uc.getContentType(), uc.getContentLength(), uc.getContentEncoding()));
            int b;
            while((b = is.read()) != -1) {
                bb.write(b);
            }
            ret = bb.toByteArray();
            log.fine(String.format("Downloaded %s bytes", ret.length));
        } catch(MalformedURLException mex) {
            throw new InvalidValueException(mex);
        } catch(IOException ioex) {
            throw new InvalidValueException(ioex);
        } finally {
            if(is != null) { try { is.close(); } catch(IOException ex) {} }
        }
        return ret;
    }


    /**
     * Read and return text file from disk
     * @param f File to read
     * @param encoding Encoding (optional). Default is to use System charset
     * @return File conent
     * @throws FileNotFoundException If file is not found
     * @throws IOException If file cannot be read
     */
    public static byte[] readTextFile(File f, Charset encoding) throws FileNotFoundException, IOException {
        byte[] ret = null;

        Charset charset = Charset.defaultCharset();
        if (encoding != null) {
            charset = encoding;
        }

        StringBuilder content = new StringBuilder();
        BufferedReader reader = null;
        try {
            char[] buf = new char[8192];
            reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            while (reader.read(buf) != -1) {
                content.append(buf);
            }
            ret = content.toString().getBytes(charset);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return ret;
    }

    /**
     * Read binary file from disk
     * @param f File to read
     * @return File content
     * @throws FileNotFoundException If file is not found
     * @throws IOException If file cannot be read
     */
    public static byte[] readBinaryFile(File f) throws FileNotFoundException, IOException {
        byte[] ret = null;
        // Binary file
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(f);
            ret = new byte[fin.available()];
            fin.read(ret);
        } finally {
            if (fin != null) {
                fin.close();
            }
        }
        return ret;
    }


    /**
     * Safely retrieve a value from request
     * @param name Name of the parameter
     * @param req Request object
     * @return Value, or empty string if not present
     */
    public static String getRequestValue(String name, HttpServletRequest req) {
        String ret = null;
        if (req != null) {
            ret = req.getParameter(name);
            if(ret == null) {
                ret = "";
            } else {
                ret = ret.trim();
            }
        }
        return ret;
    }


    /**
     * Safely get an integer parameter from request
     * @param name Name of the parameter
     * @param req Request object
     * @return Value, or 0 if not present
     */
    public static int getRequestInteger(String name, HttpServletRequest req) {
        int ret = 0;
        if (req != null) {
            String v = req.getParameter(name);
            if(v != null) {
                v = v.trim();
                try { ret = Integer.parseInt(v); } catch(Exception e) {}
            }
        }
        return ret;
    }


    /**
     * Check if parameter is on request
     * @param name Name of the parameter
     * @param req Request object
     * @return true if exists, it doesn't matter the value
     */
    public static boolean isOnRequest(String name, HttpServletRequest req) {
        if (req != null) {
            return req.getParameter(name) != null;
        }
        return false;
    }


    /**
     * Check if request contains a checkbox and this was checked
     * @param name Name of the parameter
     * @param req Request object
     * @return true if checked (value was "ON")
     */
    public static boolean getRequestCheckbox(String name, HttpServletRequest req) {
        if (req != null) {
            String value = req.getParameter(name);
            return value != null && "ON".equalsIgnoreCase(value.trim());
        }
        return false;
    }


    /**
     * Output the URL to a resource on server (i.e. image)
     * @param request Request object
     * @param path relative path to the file (optional)
     * @return Full URL to the resource
     */
    public static String url(HttpServletRequest request, String path) {
        if(path != null) {
            return String.format("%s%s", request.getContextPath(), path);
        }
        return request.getContextPath();
    }


    /**
     * Validate that all decisions are working
     * @param p Producer to retrieve data from
     * @return null if success, error message if failed
     */
    public static String validateDecisions(Producer p) {
        String ret = null;
        IDataProvider dataProvider = null;
        try {
            dataProvider = new DatabaseDataProvider();
            dataProvider.openResources();
            List<AbstractDecision> obs = p.getDecisions(dataProvider, null, 0, null);
            for(AbstractDecision ob : obs) {
                ob.validate();
            }
        } catch(Exception e) {
            ret = e.getMessage();
        } finally {
            if(dataProvider != null) {
                dataProvider.closeResources();
            }
        }
        return ret;
    }


    /**
     * Validate that all meetings are working
     * @param p Producer to retrieve data from
     * @return null if success, error message if failed
     */
   public static String validateMeetings(Producer p) {
        String ret = null;
        IDataProvider dataProvider = null;
        try {
            dataProvider = new DatabaseDataProvider();
            dataProvider.openResources();
            List<AbstractMeeting> obs = p.getMeetings(dataProvider, null, 0, null);
            for(AbstractMeeting ob : obs) {
                ob.validate();
            }
        } catch(Exception e) {
            ret = e.getMessage();
        } finally {
            if(dataProvider != null) {
                dataProvider.closeResources();
            }
        }
        return ret;
    }


    /**
     * Validate that all contacts are working
     * @param p Producer to retrieve data from
     * @return null if success, error message if failed
     */
   public static String validateContacts(Producer p) {
        String ret = null;
        IDataProvider dataProvider = null;
        try {
            dataProvider = new DatabaseDataProvider();
            dataProvider.openResources();
            List<AbstractContact> obs = p.getContacts(dataProvider, null, 0, null);
            for(AbstractContact ob : obs) {
                ob.validate();
            }
        } catch(Exception e) {
            ret = e.getMessage();
        } finally {

        }
        return ret;
    }


    /**
     * Validate that all country reports are working
     * @param p Producer to retrieve data from
     * @return null if success, error message if failed
     */
   public static String validateCountryReports(Producer p) {
        String ret = null;
        IDataProvider dataProvider = null;
        try {
            dataProvider = new DatabaseDataProvider();
            dataProvider.openResources();
            List<AbstractCountryReport> obs = p.getCountryReports(dataProvider, null, 0, null);
            for(AbstractCountryReport ob : obs) {
                ob.validate();
            }
        } catch(Exception e) {
            ret = e.getMessage();
        } finally {
            if(dataProvider != null) {
                dataProvider.closeResources();
            }
        }
        return ret;
    }


    /**
     * Validate that all country profiles are working
     * @param p Producer to retrieve data from
     * @return null if success, error message if failed
     */
    public static String validateCountryProfiles(Producer p) {
        String ret = null;
        IDataProvider dataProvider = null;
        try {
            dataProvider = new DatabaseDataProvider();
            dataProvider.openResources();
            List<AbstractCountryProfile> obs = p.getCountryProfiles(dataProvider, null, 0, null);
            for(AbstractCountryProfile ob : obs) {
                ob.getTreaty();
                ob.getCountry();
                ob.getEntryIntoForce();
                ob.getUpdated();
            }
        } catch(Exception e) {
            ret = e.getMessage();
        } finally {
            if(dataProvider != null) {
                dataProvider.closeResources();
            }
        }
        return ret;
    }


    /**
     * Validate that all national plans are working
     * @param p Producer to retrieve data from
     * @return null if success, error message if failed
     */
    public static String validateNationalPlans(Producer p) {
        String ret = null;
        IDataProvider dataProvider = null;
        try {
            dataProvider = new DatabaseDataProvider();
            dataProvider.openResources();
            List<AbstractNationalPlan> obs = p.getNationalPlans(dataProvider, null, 0, null);
            for(AbstractNationalPlan ob : obs) {
                ob.getId();
                ob.getTreaty();
                ob.getCountry();
                ob.getType();
                ob.getTitle();
                ob.getUrl();
                ob.getSubmission();
                ob.getUpdated();
            }
        } catch(Exception e) {
            ret = e.getMessage();
        } finally {
            if(dataProvider != null) {
                dataProvider.closeResources();
            }
        }
        return ret;
    }


    /**
     * Validate that all sites are working
     * @param p Producer to retrieve data from
     * @return null if success, error message if failed
     */
    public static String validateSites(Producer p) {
        String ret = null;
        IDataProvider dataProvider = null;
        try {
            dataProvider = new DatabaseDataProvider();
            dataProvider.openResources();
            List<AbstractSite> obs = p.getSites(dataProvider, null, 0, null);
            for(AbstractSite ob : obs) {
                ob.validate();
            }
        } catch(Exception e) {
            ret = e.getMessage();
        } finally {
            if(dataProvider != null) {
                dataProvider.closeResources();
            }
        }
        return ret;
    }


    /**
     * Do HTTP POST submission to an URL and return the result. At the moment uses URLConnection. Could be improved to use HttpURLConnection to handle HTTP errors.
     * @param url URL to submit to
     * @param params HTTP POST parameters key=value
     * @return The output or an empty string if error occurrs.
     */
    public static String httpPost(String url, Map<String, String> params) {
        StringBuilder ret = new StringBuilder(2000);
        try {
            // Construct data
            StringBuilder post = new StringBuilder(500);
            int c = 0, max = params.keySet().size();
            for(Map.Entry<String, String> item : params.entrySet()) {
                post.append(URLEncoder.encode(item.getKey(), "UTF-8"));
                post.append("=");
                post.append(URLEncoder.encode(item.getValue(), "UTF-8"));
                if(c < max - 1) {
                    post.append("&");
                }
                c++;
            }
            // Send data
            URL urlOb = new URL(url);
            URLConnection conn = urlOb.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(4000);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(post.toString());
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                ret.append(line);
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * Wrapper around <code>httpPost</code> method, but caches the result
     * locally on disk and retrieves it. Cache is invalidated every hour.
     * @param url URL to submit to
     * @param params HTTP POST parameters key=value
     * @return The output or an empty string if error occurs.
     * @see org.informea.odata.util.ToolkitUtil#httpPost(java.lang.String, java.util.Map)
     */
    public static String httpPostCached(String url, Map<String, String> params) {
        String ret = "";
        File cacheDir = Configuration.getInstance().getTemplateCacheDirectory();
        String filename = "informea_" + md5sum(url) + ".html";
        try {
            if(cacheDir != null) {
                if(!filename.isEmpty()) {
                    File f = new File(cacheDir, filename);
                    if(f.exists() && f.canRead()) {
                        if(f.length() != 0) {
                            if(System.currentTimeMillis() - f.lastModified() < TimeUnit.MICROSECONDS.convert(1, TimeUnit.HOURS)) {
                                log.log(Level.FINE, String.format("Found valid data in cache for URL: %s in file %s", url, f.getAbsolutePath()));
                                ret = new String(readTextFile(f, Charset.defaultCharset()));
                            } else {
                                log.log(Level.FINE, String.format("Data in cache is older than 1 hour, refreshing cache: %s in file %s", url, f.getAbsolutePath()));
                                f.delete();
                            }
                        } else {
                            log.log(Level.FINE, String.format("Found zero size file in cache for URL: %s in file %s", url, f.getAbsolutePath()));
                        }
                    }
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        if(ret == null || ret.isEmpty()) {
            ret = httpPost(url, params);
            if(ret.length() == 0) {
                log.log(Level.FINE, String.format("Zero-length answer for URL %s", url));
            }
            if(cacheDir != null && ret.length() > 0) {
                try {
                    File f = new File(cacheDir, filename);
                    log.log(Level.FINE, String.format("Caching remote resource from %s in file %s", url, f.getAbsolutePath()));
                    FileWriter outFile = new FileWriter(f);
                    PrintWriter out = new PrintWriter(outFile);
                    out.write(ret);
                    out.close();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return ret;
    }

    /**
     * Compute MD5 sum for a string
     * @param str String to compute
     * @return Hexadecimal reporesentation of the checksum
     */
    private static String md5sum(String str) {
        String ret = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] mdbytes = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i=0;i<mdbytes.length;i++) {
    		String hex=Integer.toHexString(0xff & mdbytes[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
            }
            ret = hexString.toString();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    /**
     * Generate HTML template header
     * @param request HTTP request
     * @return header as HTML string
     */
    public static String templateHeader(HttpServletRequest request) {
        String template_header = ToolkitUtil.httpPostCached(Configuration.getInstance().getTemplateHeaderUrl(), new HashMap<String, String>());
        template_header = template_header.replaceAll("<span id=\"informea_template_primary_menu\"></span>", templatePartPrimaryMenu(request));
        template_header = template_header.replaceAll("<span id=\"informea_template_useful_links_menu\"></span>", templatePartUsefulLinks(request));
        template_header = template_header.replaceAll("<span id=\"informea_template_breadcrumbtrail\"></span>", templatePartBreadcrumbtrail(request));

        return template_header;
    }

    /**
     * Generate breadcrumbtrail part of the template
     * @param request HTTP request
     * @return HTML code
     */
    @SuppressWarnings("unchecked")
	public static String templatePartBreadcrumbtrail(HttpServletRequest request) {
        String ret = "";
        Map<String, String> breadcrumbtrail =  (Map<String, String>)request.getAttribute("breadcrumbtrail");
        if(null == breadcrumbtrail) {
            breadcrumbtrail = new LinkedHashMap<String, String>();
        }
        if(!breadcrumbtrail.containsKey("Home")) {
            breadcrumbtrail.put("Home", url(request, "/"));
        }
        if(breadcrumbtrail != null) {
            int c = 0, max = breadcrumbtrail.size();
            for(Map.Entry<String, String> entry : breadcrumbtrail.entrySet()) {
                if(!entry.getValue().isEmpty()) {
                    ret += String.format("<a href=\"%s\">%s</a>", entry.getValue(), entry.getKey());
                } else {
                    ret += String.format(entry.getKey());
                }
                if(c < max - 1) {
                    ret += " &raquo; ";
                }
                c++;
            }

            if(!ret.isEmpty()) {
                ret = "<div id=\"breadcrumb\" class=\"clear\"><span class=\"breadcrumb-name\">You are here: </span>" + ret + "</div>";
            }
        }
        return ret;
    }

    /**
     * Generate primary menu part of the template
     * @param request HTTP request
     * @return HTML code
     */
    @SuppressWarnings("serial")
	public static String templatePartPrimaryMenu(final HttpServletRequest request) {
        final String current_item = request.getParameter("current_menu_item");

        Map<String, Map<String, String>> menu_items = new LinkedHashMap<String, Map<String, String>>();
        menu_items.put("1mm", new HashMap<String, String>() {{
            this.put("url", ToolkitUtil.url(request, "/"));
            this.put("label", "Home");
            this.put("current", "".equalsIgnoreCase(current_item) ? "1" : "0");
        }});
        if(Configuration.getInstance().isInstalled()) {
            menu_items.put("2mm", new HashMap<String, String>() {{
                this.put("url", ToolkitUtil.url(request, "/status.jsp"));
                this.put("label", "Status");
                this.put("current", "status".equalsIgnoreCase(current_item) ? "1" : "0");
            }});
        }
        menu_items.put("3mm", new HashMap<String, String>() {{
            this.put("url", ToolkitUtil.url(request, "/configuration"));
            this.put("label", "Configuration");
            this.put("current", "configuration".equalsIgnoreCase(current_item) ? "1" : "0");
        }});
        if(request.getUserPrincipal() != null) {
            menu_items.put("4mm", new HashMap<String, String>() {{
                this.put("url", ToolkitUtil.url(request, "/sql"));
                this.put("label", "SQL Console");
                this.put("current", "sql".equalsIgnoreCase(current_item) ? "1" : "0");
            }});
            menu_items.put("5mm", new HashMap<String, String>() {{
                this.put("url", ToolkitUtil.url(request, "/faq"));
                this.put("label", "FAQ");
                this.put("current", "faq".equalsIgnoreCase(current_item) ? "1" : "0");
            }});
            menu_items.put("6mm", new HashMap<String, String>() {{
                this.put("url", ToolkitUtil.url(request, "/tests"));
                this.put("label", "Tests");
                this.put("current", "tests".equalsIgnoreCase(current_item) ? "1" : "0");
            }});
        }
        String menu = "";
        for(Map.Entry<String, Map<String, String>> key : menu_items.entrySet()) {
            Map<String, String> ob = key.getValue();
            String current = "1".equalsIgnoreCase(ob.get("current")) ? " current-menu-item current_page_item" : "";
            menu += String.format("<li class=\"menu-item menu-item-type-post_type menu-item-object-page%s\"><a href=\"%s\">%s</a></li>", current, ob.get("url"), ob.get("label"));
        }
        return "<div class=\"menu-header\"><ul id=\"menu-main\" class=\"menu\">" + menu + "</ul></div>";
    }

    /**
     * Generate useful links template part
     * @param request HTTP request
     * @return HTML code
     */
    public static String templatePartUsefulLinks(final HttpServletRequest request) {
        String ret = String.format("InforMEA Toolkit v. %s.%s.%s%s", Producer.MAJOR, Producer.MINOR, Producer.REVISION, Producer.BETA ? "b" : "");
        if(request.getUserPrincipal() != null) {
            ret += " | " + String.format("Welcome, <strong>%s</strong>", request.getUserPrincipal().getName());
        }
        return ret;
    }
}
