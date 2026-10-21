import os
import docx
from docx import Document
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT, WD_ALIGN_VERTICAL
from docx.oxml import OxmlElement, parse_xml
from docx.oxml.ns import nsdecls, qn

def set_cell_background(cell, hex_color):
    tcPr = cell._tc.get_or_add_tcPr()
    shd = parse_xml(f'<w:shd {nsdecls("w")} w:fill="{hex_color}"/>')
    tcPr.append(shd)

def set_cell_margins(cell, top=100, bottom=100, left=150, right=150):
    tcPr = cell._tc.get_or_add_tcPr()
    tcMar = parse_xml(f'<w:tcMar {nsdecls("w")}><w:top w:w="{top}" w:type="dxa"/><w:bottom w:w="{bottom}" w:type="dxa"/><w:left w:w="{left}" w:type="dxa"/><w:right w:w="{right}" w:type="dxa"/></w:tcMar>')
    tcPr.append(tcMar)

def create_report():
    doc = Document()

    # Page Margins (1 inch all around)
    for section in doc.sections:
        section.top_margin = Inches(1.0)
        section.bottom_margin = Inches(1.0)
        section.left_margin = Inches(1.0)
        section.right_margin = Inches(1.0)

    # Palette
    COLOR_PRIMARY = RGBColor(30, 60, 114)     # #1E3C72 Deep Blue
    COLOR_SECONDARY = RGBColor(255, 111, 0)   # #FF6F00 Vibrant Orange
    COLOR_DARK = RGBColor(44, 62, 80)         # #2C3E50 Dark Slate
    COLOR_MUTED = RGBColor(108, 117, 125)     # #6C757D Gray
    HEX_PRIMARY = "1E3C72"
    HEX_LIGHT_BG = "F4F6F9"
    HEX_BORDER = "CCCCCC"

    # Set Base Style Font
    style_normal = doc.styles['Normal']
    font = style_normal.font
    font.name = 'Calibri'
    font.size = Pt(11)
    font.color.rgb = COLOR_DARK

    # -------------------------------------------------------------
    # COVER PAGE
    # -------------------------------------------------------------
    title_p = doc.add_paragraph()
    title_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    title_p.paragraph_format.space_before = Pt(36)
    title_p.paragraph_format.space_after = Pt(6)
    run_icon = title_p.add_run("🚆\n")
    run_icon.font.size = Pt(36)

    run_title = title_p.add_run("RAILRUSH (TRACKPULSE)")
    run_title.font.name = 'Arial'
    run_title.font.size = Pt(28)
    run_title.font.bold = True
    run_title.font.color.rgb = COLOR_PRIMARY

    sub_p = doc.add_paragraph()
    sub_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    sub_p.paragraph_format.space_after = Pt(24)
    run_sub = sub_p.add_run("Next-Generation Railway Ticket Reservation & Real-Time Train Movement Telemetry System")
    run_sub.font.name = 'Calibri'
    run_sub.font.size = Pt(14)
    run_sub.font.italic = True
    run_sub.font.color.rgb = COLOR_SECONDARY

    # Divider Line
    line_p = doc.add_paragraph()
    line_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    line_p.paragraph_format.space_after = Pt(40)
    run_line = line_p.add_run("―" * 40)
    run_line.font.color.rgb = COLOR_PRIMARY

    # Project Context Box
    meta_p = doc.add_paragraph()
    meta_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    meta_p.paragraph_format.space_after = Pt(50)
    run_desc = meta_p.add_run(
        "A Comprehensive Full-Stack Java (FSJ) Engineering Mini-Project\n"
        "Demonstrating Core Object-Oriented Programming, Jakarta Servlets 6.0, JSP 3.1,\n"
        "JDBC PreparedStatements, ACID Transactions, and MySQL Persistence"
    )
    run_desc.font.size = Pt(11.5)
    run_desc.font.color.rgb = COLOR_DARK

    # Student / Submission Details Table
    details_tbl = doc.add_table(rows=5, cols=2)
    details_tbl.alignment = WD_TABLE_ALIGNMENT.CENTER
    details = [
        ("Candidate / Student Name:", "Aarya Chavan"),
        ("Course / Laboratory:", "Full-Stack Java (FSJ) Programming Laboratory"),
        ("Project Repository:", "https://github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-"),
        ("Primary Architecture:", "3-Tier MVC (JSP + Jakarta Servlets + JDBC + MySQL)"),
        ("Academic Year:", "2026 – 2027")
    ]
    for i, (k, v) in enumerate(details):
        row = details_tbl.rows[i]
        c0, c1 = row.cells[0], row.cells[1]
        c0.width = Inches(2.5)
        c1.width = Inches(4.0)
        p0 = c0.paragraphs[0]
        p0.paragraph_format.space_before = Pt(3)
        p0.paragraph_format.space_after = Pt(3)
        r0 = p0.add_run(k)
        r0.font.bold = True
        r0.font.color.rgb = COLOR_PRIMARY
        
        p1 = c1.paragraphs[0]
        p1.paragraph_format.space_before = Pt(3)
        p1.paragraph_format.space_after = Pt(3)
        r1 = p1.add_run(v)
        r1.font.color.rgb = COLOR_DARK
        set_cell_margins(c0, 60, 60, 80, 80)
        set_cell_margins(c1, 60, 60, 80, 80)

    doc.add_page_break()

    # -------------------------------------------------------------
    # HELPER FUNCTIONS FOR CHAPTERS & SECTIONS
    # -------------------------------------------------------------
    def add_heading_1(text):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(20)
        p.paragraph_format.space_after = Pt(8)
        p.paragraph_format.keep_with_next = True
        run = p.add_run(text)
        run.font.name = 'Arial'
        run.font.size = Pt(16)
        run.font.bold = True
        run.font.color.rgb = COLOR_PRIMARY
        return p

    def add_heading_2(text):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(14)
        p.paragraph_format.space_after = Pt(4)
        p.paragraph_format.keep_with_next = True
        run = p.add_run(text)
        run.font.name = 'Arial'
        run.font.size = Pt(13)
        run.font.bold = True
        run.font.color.rgb = COLOR_SECONDARY
        return p

    def add_heading_3(text):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(10)
        p.paragraph_format.space_after = Pt(2)
        p.paragraph_format.keep_with_next = True
        run = p.add_run(text)
        run.font.name = 'Arial'
        run.font.size = Pt(11)
        run.font.bold = True
        run.font.color.rgb = COLOR_DARK
        return p

    def add_body(text, space_after=6):
        p = doc.add_paragraph()
        p.paragraph_format.space_after = Pt(space_after)
        p.paragraph_format.line_spacing = 1.15
        run = p.add_run(text)
        run.font.size = Pt(11)
        run.font.color.rgb = COLOR_DARK
        return p

    def add_bullet(bold_prefix, text):
        p = doc.add_paragraph(style='List Bullet')
        p.paragraph_format.space_after = Pt(3)
        p.paragraph_format.line_spacing = 1.15
        r_bold = p.add_run(bold_prefix)
        r_bold.font.bold = True
        r_bold.font.color.rgb = COLOR_PRIMARY
        r_text = p.add_run(text)
        r_text.font.color.rgb = COLOR_DARK
        return p

    def style_table_header(table, headers, hex_bg=HEX_PRIMARY):
        hdr_row = table.rows[0]
        for idx, title in enumerate(headers):
            cell = hdr_row.cells[idx]
            set_cell_background(cell, hex_bg)
            set_cell_margins(cell, top=120, bottom=120, left=120, right=120)
            p = cell.paragraphs[0]
            p.alignment = WD_ALIGN_PARAGRAPH.LEFT
            p.paragraph_format.space_before = Pt(2)
            p.paragraph_format.space_after = Pt(2)
            r = p.add_run(title)
            r.font.bold = True
            r.font.size = Pt(10)
            r.font.color.rgb = RGBColor(255, 255, 255)

    # -------------------------------------------------------------
    # CERTIFICATE & DECLARATION
    # -------------------------------------------------------------
    add_heading_1("CERTIFICATE OF AUTHENTICITY")
    add_body(
        "This is to certify that the project entitled “RailRush (TrackPulse): Railway Ticket Reservation "
        "and Real-Time Train Movement Telemetry System” is a bona fide engineering mini-project submitted by "
        "Aarya Chavan in partial fulfillment of the requirements for the Full-Stack Java (FSJ) Engineering Laboratory."
    )
    add_body(
        "The project has been planned, implemented, tested, and documented in adherence to modern software "
        "engineering practices and covers all required curriculum experiments including Object-Oriented Modeling, "
        "Exception Handling, Generic Data Access Objects, Jakarta Servlets, JSP Scripting/Actions, and MySQL Database Transactions."
    )
    
    cert_tbl = doc.add_table(rows=2, cols=2)
    cert_tbl.alignment = WD_TABLE_ALIGNMENT.CENTER
    cert_tbl.rows[0].cells[0].paragraphs[0].add_run("\n\n___________________________\nProject Guide / Faculty Mentor").font.bold = True
    cert_tbl.rows[0].cells[1].paragraphs[0].add_run("\n\n___________________________\nHead of Department").font.bold = True
    cert_tbl.rows[1].cells[0].paragraphs[0].add_run("Date: October 21, 2026").font.color.rgb = COLOR_MUTED
    cert_tbl.rows[1].cells[1].paragraphs[0].add_run("Department of Computer Engineering").font.color.rgb = COLOR_MUTED
    
    doc.add_page_break()

    # -------------------------------------------------------------
    # ABSTRACT & EXECUTIVE SUMMARY
    # -------------------------------------------------------------
    add_heading_1("ABSTRACT")
    add_body(
        "Railway networks represent the lifeline of public transit, demanding resilient, high-throughput digital ticketing "
        "and real-time train movement monitoring. The RailRush (TrackPulse) system is a full-stack enterprise web application "
        "crafted to address critical shortcomings in transit portals, including high-concurrency race conditions during seat "
        "allocation, lack of instant telemetry for en-route trains, and complicated user reservation flows."
    )
    add_body(
        "Architected using a decoupled 3-Tier Model-View-Controller (MVC) standard, RailRush integrates Jakarta Servlets 6.0 "
        "as controller components, JavaServer Pages (JSP 3.1) for dynamic presentation, and MySQL 8.0 for relational persistence. "
        "The core reservation engine enforces strict ACID compliance using JDBC transaction isolation and row-level locking "
        "(SELECT ... FOR UPDATE) to eliminate double-booking anomalies. Concurrently, the integrated TrackPulse telemetry module "
        "offers live lookup of running fleet status, platform allocation, and intermediate junction delays."
    )
    add_body(
        "Key outcomes of the system include instantaneous 10-digit Passenger Name Record (PNR) generation, role-based "
        "session authorization, responsive client-side validation, and complete alignment with academic software engineering "
        "experiments from fundamental OOP to enterprise persistence."
    )

    doc.add_page_break()

    # -------------------------------------------------------------
    # CHAPTER 1: INTRODUCTION & PROBLEM STATEMENT
    # -------------------------------------------------------------
    add_heading_1("CHAPTER 1: INTRODUCTION & PROBLEM STATEMENT")
    
    add_heading_2("1.1 Background & Transit Landscape")
    add_body(
        "Modern transportation systems, particularly large-scale railway networks such as Indian Railways, cater to millions "
        "of passengers each day across thousands of route combinations. Traditional manual ticketing and legacy booking websites "
        "frequently suffer from server timeouts, uncoordinated concurrent bookings during peak Tatkal hours, and disconnected "
        "telemetry updates. Passengers are often forced to switch between disparate applications to reserve tickets and monitor "
        "train running status."
    )

    add_heading_2("1.2 Problem Statement & Bottlenecks")
    add_body("Key challenges identified in existing transit infrastructures include:")
    add_bullet("Race Conditions in Seat Inventory: ", "Concurrent reservation requests for the final available seats can result in overselling or inconsistent seat state without explicit transactional database locking.")
    add_bullet("Fragmented User Experience: ", "Disjointed interfaces separating schedule inquiries, booking portals, printable E-tickets, and live train delays.")
    add_bullet("Input Vulnerability & Data Integrity: ", "Absence of client-side DOM validation leading to invalid age, negative seat counts, and malformed email inputs hitting server endpoints.")
    add_bullet("Lack of Structured Telemetry: ", "Delay indicators and station arrival data are rarely co-located with active passenger tickets.")

    add_heading_2("1.3 Objectives of RailRush")
    add_bullet("ACID-Compliant Ticket Engine: ", "Implement transactional seat decrementing, booking persistence, and passenger manifest insertion within an atomic commit/rollback boundary.")
    add_bullet("Automated PNR Allocation: ", "Generate unique, cryptographically random 10-digit PNR strings for instant retrieval and printable e-ticket rendering.")
    add_bullet("Real-Time Fleet Telemetry (TrackPulse): ", "Provide live station-to-station movement, platform indicators, and delay tracking with automatic browser synchronization.")
    add_bullet("Role-Based Session Management: ", "Maintain secure HTTP session lifecycles for passenger dashboards and administrative controls.")
    add_bullet("Academic Rigor: ", "Demonstrate rigorous application of core Full-Stack Java competencies from Class design to Jakarta EE deployment.")

    # -------------------------------------------------------------
    # CHAPTER 2: ACADEMIC CURRICULUM ALIGNMENT (FSJ LAB)
    # -------------------------------------------------------------
    add_heading_1("CHAPTER 2: ACADEMIC CURRICULUM ALIGNMENT")
    add_body(
        "RailRush is engineered specifically to demonstrate every mandatory experiment prescribed in the "
        "Full-Stack Java (FSJ) Laboratory syllabus, bridging theoretical concepts with industrial-grade implementations:"
    )

    exp_table = doc.add_table(rows=8, cols=3)
    exp_table.alignment = WD_TABLE_ALIGNMENT.CENTER
    exp_headers = ["Exp No.", "Syllabus Laboratory Topic", "Concrete Implementation in RailRush"]
    style_table_header(exp_table, exp_headers)
    
    exp_data = [
        ("Exp 01", "Classes, Encapsulation & Objects", "Domain model encapsulation with private members, standard getters/setters in User, Train, Ticket, and Passenger."),
        ("Exp 02", "Constructor & Method Overloading", "Overloaded constructors in Train.java (minimal lookup vs full tariff route); overloaded searchTrain() signatures."),
        ("Exp 03", "Inheritance & Custom Exceptions", "Hierarchical inheritance (PassengerUser extends User); Custom exceptions: SeatUnavailableException, InvalidPNRException, BookingFailedException."),
        ("Exp 04", "Generic DAO & HTTP Servlets", "GenericDAO<T, ID> interface for type-safe persistence; Jakarta Servlets (BookTicketServlet, SearchTrainServlet, TrackPulseServlet) managing doGet/doPost."),
        ("Exp 05", "JDBC PreparedStatements & MySQL", "Singleton DBConnection pool executing parameterized queries in TrainDAO and BookingDAO to prevent SQL Injection attacks."),
        ("Exp 06", "JSP Implicit Objects & Action Tags", "Session authentication with 'session', dynamic output with 'out', and standard action tags <jsp:useBean> and <jsp:getProperty> in dashboard.jsp and ticket_confirmation.jsp."),
        ("Exp 07", "Client-Side JavaScript Validation", "DOM event handlers in validation.js validating email regex, station matching, age bounds (1–120), and 10-digit PNR integrity.")
    ]

    for idx, (exp_no, topic, impl) in enumerate(exp_data, start=1):
        row = exp_table.rows[idx]
        row.cells[0].width = Inches(1.0)
        row.cells[1].width = Inches(2.2)
        row.cells[2].width = Inches(3.3)
        for c_idx, text in enumerate([exp_no, topic, impl]):
            cell = row.cells[c_idx]
            if idx % 2 == 1:
                set_cell_background(cell, HEX_LIGHT_BG)
            set_cell_margins(cell, top=80, bottom=80, left=90, right=90)
            p = cell.paragraphs[0]
            p.paragraph_format.space_before = Pt(2)
            p.paragraph_format.space_after = Pt(2)
            r = p.add_run(text)
            r.font.size = Pt(9.5)
            if c_idx == 0:
                r.font.bold = True
                r.font.color.rgb = COLOR_PRIMARY

    # -------------------------------------------------------------
    # CHAPTER 3: SYSTEM ARCHITECTURE & TECHNICAL SPECIFICATIONS
    # -------------------------------------------------------------
    doc.add_page_break()
    add_heading_1("CHAPTER 3: SYSTEM ARCHITECTURE & DESIGN")
    
    add_heading_2("3.1 3-Tier Layered Architecture")
    add_body(
        "The RailRush system adheres to the standard 3-Tier Model-View-Controller (MVC) enterprise architecture, "
        "ensuring strict separation of concerns, maintainability, and scalability:"
    )
    add_bullet("Presentation Layer (View): ", "Composed of JSP pages (index.jsp, dashboard.jsp, booking.jsp, track_pulse.jsp), CSS3 design system, and ES6 JavaScript. Dynamic content is rendered using JSP Scriptlets and JavaBean action tags.")
    add_bullet("Controller & Business Layer: ", "Jakarta Servlets intercept client requests, parse form parameters, manage session security (HttpSession), and coordinate transactional booking engines.")
    add_bullet("Data Access Layer (DAO): ", "GenericDAO interface and specialized implementations (TrainDAO, BookingDAO) encapsulate all SQL execution via JDBC PreparedStatements.")
    add_bullet("Persistence Layer: ", "MySQL 8.0 relational database holding normalized tables for users, fleet schedules, bookings, passenger manifests, and live telemetry.")

    add_heading_2("3.2 ACID Transaction & Concurrency Model")
    add_body(
        "Seat allocation in railway ticketing is prone to concurrency bugs if multiple requests access the same inventory concurrently. "
        "RailRush enforces strict database transactions in BookingDAO.java:"
    )
    add_bullet("1. Begin Transaction: ", "Invokes conn.setAutoCommit(false) to group operations into an atomic unit.")
    add_bullet("2. Pessimistic Row Locking: ", "Executes SELECT available_seats FROM trains WHERE train_number = ? FOR UPDATE to acquire an exclusive row lock.")
    add_bullet("3. Validation: ", "Verifies that available_seats >= requested seats. Throws SeatUnavailableException if capacity is exceeded.")
    add_bullet("4. Inventory Decrement: ", "Executes UPDATE trains SET available_seats = available_seats - ?.")
    add_bullet("5. Record Insertion: ", "Inserts booking master record (PNR, user_id, class, total_fare) and batch inserts individual passengers.")
    add_bullet("6. Atomic Commit / Rollback: ", "Executes conn.commit() upon successful execution; triggers conn.rollback() on any SQLException to maintain database consistency.")

    # -------------------------------------------------------------
    # CHAPTER 4: DATABASE DESIGN & RELATIONAL SCHEMA
    # -------------------------------------------------------------
    add_heading_1("CHAPTER 4: DATABASE DESIGN & RELATIONAL SCHEMA")
    add_body(
        "The relational database schema is named 'railrush_db' and is composed of five normalized tables "
        "enforcing primary key uniqueness and foreign key cascade integrity."
    )

    db_tables = [
        ("users", "User accounts, role classifications, authentication hashes, and contact information."),
        ("trains", "Master train registry, route stations, arrival/departure schedules, seat capacities, and fares."),
        ("bookings", "Transactional reservation headers with 10-digit PNR, travel class, total fare, and booking status."),
        ("passengers", "Individual passenger records linked to PNR foreign key with assigned coach seat identifiers."),
        ("live_tracking", "TrackPulse telemetry records maintaining current stations, en-route stops, and delay minutes.")
    ]

    tbl_db = doc.add_table(rows=6, cols=2)
    tbl_db.alignment = WD_TABLE_ALIGNMENT.CENTER
    style_table_header(tbl_db, ["Table Name", "Description & Schema Scope"])

    for idx, (tname, tdesc) in enumerate(db_tables, start=1):
        row = tbl_db.rows[idx]
        row.cells[0].width = Inches(1.8)
        row.cells[1].width = Inches(4.7)
        if idx % 2 == 1:
            set_cell_background(row.cells[0], HEX_LIGHT_BG)
            set_cell_background(row.cells[1], HEX_LIGHT_BG)
        set_cell_margins(row.cells[0], 60, 60, 80, 80)
        set_cell_margins(row.cells[1], 60, 60, 80, 80)
        
        p0 = row.cells[0].paragraphs[0]
        p0.paragraph_format.space_before = Pt(2)
        p0.paragraph_format.space_after = Pt(2)
        r0 = p0.add_run(tname)
        r0.font.bold = True
        r0.font.color.rgb = COLOR_PRIMARY
        
        p1 = row.cells[1].paragraphs[0]
        p1.paragraph_format.space_before = Pt(2)
        p1.paragraph_format.space_after = Pt(2)
        p1.add_run(tdesc)

    add_heading_2("4.2 SQL Table Definition Excerpt")
    sql_code = (
        "CREATE TABLE bookings (\n"
        "    pnr_number VARCHAR(10) PRIMARY KEY,\n"
        "    user_id INT NOT NULL,\n"
        "    train_number VARCHAR(10) NOT NULL,\n"
        "    journey_date DATE NOT NULL,\n"
        "    travel_class ENUM('SLEEPER', 'AC_3_TIER', 'AC_2_TIER', 'AC_FIRST') NOT NULL,\n"
        "    total_fare DECIMAL(10,2) NOT NULL,\n"
        "    status ENUM('CONFIRMED', 'WAITLISTED', 'CANCELLED') DEFAULT 'CONFIRMED',\n"
        "    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,\n"
        "    FOREIGN KEY (train_number) REFERENCES trains(train_number) ON DELETE CASCADE\n"
        ");"
    )
    p_code = doc.add_paragraph()
    p_code.paragraph_format.space_before = Pt(6)
    p_code.paragraph_format.space_after = Pt(12)
    p_code.paragraph_format.left_indent = Inches(0.3)
    r_code = p_code.add_run(sql_code)
    r_code.font.name = 'Consolas'
    r_code.font.size = Pt(9.5)
    r_code.font.color.rgb = COLOR_PRIMARY

    # -------------------------------------------------------------
    # CHAPTER 5: DETAILED MODULE IMPLEMENTATION
    # -------------------------------------------------------------
    doc.add_page_break()
    add_heading_1("CHAPTER 5: DETAILED MODULE IMPLEMENTATION")
    
    add_heading_2("5.1 Model Layer Components")
    add_bullet("User.java & PassengerUser.java: ", "Implements core user credentials and hierarchical inheritance, storing loyalty points and preferred travel class.")
    add_bullet("Train.java: ", "Encapsulates schedule times, route station endpoints, seat inventory, and overloaded constructors for dynamic query results.")
    add_bullet("Ticket.java: ", "JavaBean conforming to standard specifications, containing full journey parameters, fare details, and a List<Passenger> collection.")
    add_bullet("LiveStatus.java: ", "Telemetry encapsulation representing current station, estimated delay, and status update timestamps.")

    add_heading_2("5.2 Controller / Servlet Layer")
    add_bullet("LoginServlet.java: ", "Validates email and password using JDBC PreparedStatement, sets session attributes ('userId', 'userName', 'userRole'), and redirects to dashboard.jsp.")
    add_bullet("SearchTrainServlet.java: ", "Processes GET/POST origin and destination queries, queries TrainDAO, and forwards request scope attributes to search_results.jsp.")
    add_bullet("BookTicketServlet.java: ", "Coordinates session verification, seat quota checking, fare calculation, and invokes BookingDAO.bookTicket().")
    add_bullet("TrackPulseServlet.java: ", "Filters fleet telemetry by optional train number parameter and forwards real-time status feeds to track_pulse.jsp.")

    add_heading_2("5.3 Presentation & View Layer")
    add_bullet("index.jsp: ", "Modern portal landing page with origin/destination dropdowns and quick links to live telemetry.")
    add_bullet("dashboard.jsp: ", "Personalized passenger view utilizing JSP implicit session objects and explicit <jsp:useBean> and <jsp:getProperty> tags.")
    add_bullet("ticket_confirmation.jsp: ", "E-ticket summary displaying the 10-digit PNR, passenger berth allocation, and print stylesheet.")
    add_bullet("track_pulse.jsp: ", "Telemetry monitor featuring automatic 30-second meta-refresh for live station progress.")

    add_heading_2("5.4 Client-Side Validation (validation.js)")
    add_body(
        "Client-side verification ensures that only sanitized inputs are transmitted to the backend servlets. "
        "Functions include validateLoginForm(), validateSearchForm() (ensuring source != destination), "
        "validateBookingForm() (enforcing passenger age between 1 and 120), and validatePNRForm() (verifying exact 10 numeric digits)."
    )

    # -------------------------------------------------------------
    # CHAPTER 6: SYSTEM REQUIREMENTS & DEPLOYMENT GUIDE
    # -------------------------------------------------------------
    add_heading_1("CHAPTER 6: SYSTEM REQUIREMENTS & DEPLOYMENT GUIDE")
    
    add_heading_2("6.1 Hardware & Software Prerequisites")
    req_tbl = doc.add_table(rows=7, cols=2)
    req_tbl.alignment = WD_TABLE_ALIGNMENT.CENTER
    style_table_header(req_tbl, ["Requirement Domain", "Recommended Specification"])
    
    req_data = [
        ("Operating System", "Microsoft Windows 10 / 11, Linux (Ubuntu/Debian), or macOS"),
        ("Java Development Kit", "Oracle JDK or Eclipse Temurin OpenJDK 17 or higher (Java 26 verified)"),
        ("Servlet Container", "Apache Tomcat 10.1+ (Jakarta EE 10 namespace compliant)"),
        ("Database Engine", "MySQL Community Server 8.0 or higher"),
        ("Database Client", "MySQL Workbench 8.0 or MySQL CLI"),
        ("Build Tool / IDE", "Apache Maven 3.8+, Eclipse IDE for Enterprise Java / IntelliJ IDEA")
    ]
    for idx, (domain, spec) in enumerate(req_data, start=1):
        row = req_tbl.rows[idx]
        row.cells[0].width = Inches(2.2)
        row.cells[1].width = Inches(4.3)
        if idx % 2 == 1:
            set_cell_background(row.cells[0], HEX_LIGHT_BG)
            set_cell_background(row.cells[1], HEX_LIGHT_BG)
        set_cell_margins(row.cells[0], 60, 60, 80, 80)
        set_cell_margins(row.cells[1], 60, 60, 80, 80)
        row.cells[0].paragraphs[0].add_run(domain).font.bold = True
        row.cells[1].paragraphs[0].add_run(spec)

    add_heading_2("6.2 Step-by-Step Setup Guide")
    add_bullet("Step 1: Database Initialization: ", "Import schema.sql into MySQL Server using 'source /path/to/schema.sql;'. This creates railrush_db and seeds default trains and demo users.")
    add_bullet("Step 2: Database Connection Configuration: ", "Open DBConnection.java and verify MySQL URL, username, and password credentials.")
    add_bullet("Step 3: Server Deployment: ", "Import project into Eclipse or IntelliJ as an Existing Maven Project, link Apache Tomcat 10.1 runtime, and run on server.")
    add_bullet("Step 4: Access Application: ", "Open browser and navigate to http://localhost:8080/RailRush/.")

    # -------------------------------------------------------------
    # CHAPTER 7: TESTING & VERIFICATION
    # -------------------------------------------------------------
    doc.add_page_break()
    add_heading_1("CHAPTER 7: TESTING & VERIFICATION")
    add_body(
        "A comprehensive testing suite was executed across the application layers to ensure functional accuracy, "
        "security, and transaction resilience:"
    )

    tc_tbl = doc.add_table(rows=7, cols=5)
    tc_tbl.alignment = WD_TABLE_ALIGNMENT.CENTER
    style_table_header(tc_tbl, ["Test ID", "Test Scenario", "Input Data", "Expected Output", "Result"])
    
    test_cases = [
        ("TC-01", "User Login", "Valid email & password (user@123)", "Session created, redirects to dashboard.jsp", "PASS"),
        ("TC-02", "User Login Failure", "Invalid password / empty email", "Error alert displayed on login.jsp", "PASS"),
        ("TC-03", "Train Search", "Origin: CSMT, Dest: Pune", "Matching trains displayed with seat count", "PASS"),
        ("TC-04", "Identical Stations", "Origin: Dadar, Dest: Dadar", "JS alert 'Source & Destination cannot match'", "PASS"),
        ("TC-05", "ACID Seat Booking", "Train: 12051, Seats: 1, Class: SL", "Seat count decremented, 10-digit PNR returned", "PASS"),
        ("TC-06", "Overselling Prevention", "Request seats > available_seats", "SeatUnavailableException thrown, booking rolled back", "PASS")
    ]

    for idx, (t_id, scen, inp, exp, res) in enumerate(test_cases, start=1):
        row = tc_tbl.rows[idx]
        row.cells[0].width = Inches(0.8)
        row.cells[1].width = Inches(1.5)
        row.cells[2].width = Inches(1.5)
        row.cells[3].width = Inches(2.0)
        row.cells[4].width = Inches(0.7)
        if idx % 2 == 1:
            for c in row.cells:
                set_cell_background(c, HEX_LIGHT_BG)
        for c_idx, val in enumerate([t_id, scen, inp, exp, res]):
            cell = row.cells[c_idx]
            set_cell_margins(cell, 60, 60, 70, 70)
            p = cell.paragraphs[0]
            p.paragraph_format.space_before = Pt(2)
            p.paragraph_format.space_after = Pt(2)
            r = p.add_run(val)
            r.font.size = Pt(9)
            if c_idx == 0:
                r.font.bold = True
            elif c_idx == 4:
                r.font.bold = True
                r.font.color.rgb = RGBColor(40, 167, 69)

    # -------------------------------------------------------------
    # CHAPTER 8: CONCLUSION & FUTURE ROADMAP
    # -------------------------------------------------------------
    add_heading_1("CHAPTER 8: CONCLUSION & FUTURE ROADMAP")
    
    add_heading_2("8.1 Summary of Outcomes")
    add_body(
        "The RailRush (TrackPulse) project successfully demonstrates an enterprise-grade full-stack Java web application "
        "tailored for high-reliability transit operations. The application fulfills all technical requirements, including "
        "thread-safe database connection pooling, ACID transactional booking, dynamic PNR lifecycle management, and live "
        "fleet telemetry tracking."
    )

    add_heading_2("8.2 Future Enhancements")
    add_bullet("AI-Powered Tatkal Waitlist Predictor: ", "Machine learning models analyzing historical reservation trends to forecast waitlist confirmation probability.")
    add_bullet("Integrated UPI / Razorpay Payment Sandbox: ", "Enabling end-to-end digital payment capture prior to PNR issuance.")
    add_bullet("GPS Hardware Telemetry Feeds: ", "Direct integration with train-mounted GPS beacons for live geospatial coordinate tracking on interactive maps.")
    add_bullet("Mobile Companion Application: ", "Cross-platform mobile application built on Flutter or React Native leveraging Spring Boot REST APIs.")

    # -------------------------------------------------------------
    # REFERENCES & BIBLIOGRAPHY
    # -------------------------------------------------------------
    add_heading_1("REFERENCES & BIBLIOGRAPHY")
    add_bullet("1. ", "Oracle Corporation, 'Jakarta EE Specification Documentation', https://jakarta.ee/specifications/.")
    add_bullet("2. ", "Joshua Bloch, 'Effective Java', 3rd Edition, Addison-Wesley Professional.")
    add_bullet("3. ", "MySQL AB, 'MySQL 8.0 Reference Manual: Transactional and Locking Statements', https://dev.mysql.com/doc/.")
    add_bullet("4. ", "Apache Software Foundation, 'Apache Tomcat 10 Documentation', https://tomcat.apache.org/tomcat-10.1-doc/.")
    add_bullet("5. ", "Project Source Code Repository, https://github.com/aaryachavan076-gif/Project-Title-RailRush-or-TrackPulse-.")

    # Save Document
    target_docs_path = r"c:\Users\Aarya Mangesh Chavan\Downloads\FSJ MIN PROJECT\docs\RailRush_Project_Report.docx"
    target_root_path = r"c:\Users\Aarya Mangesh Chavan\Downloads\FSJ MIN PROJECT\RailRush_Project_Report.docx"
    
    doc.save(target_docs_path)
    doc.save(target_root_path)
    print(f"Successfully generated Word reports:\n1. {target_docs_path}\n2. {target_root_path}")

if __name__ == "__main__":
    create_report()
