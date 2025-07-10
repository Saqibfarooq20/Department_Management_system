import React from 'react';
import '../Styles/Contact.css';

const Contact = () => {
  return (
    <div className="contact-container">
      <h2>Contact Us</h2>
      <p>Have questions? We'd love to hear from you.</p>
      <form className="contact-form">
        <label htmlFor="name">Name</label>
        <input type="text" id="name" placeholder="Your Name" required />

        <label htmlFor="email">Email</label>
        <input type="email" id="email" placeholder="Your Email" required />

        <label htmlFor="message">Message</label>
        <textarea id="message" rows="5" placeholder="Your Message" required />

        <button type="submit">Send Message</button>
      </form>
    </div>
  );
};

export default Contact;
