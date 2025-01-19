import  { useState } from 'react';
import { Link } from 'react-router-dom';

type prop ={
    title : string;
}

const Navbar = ({title} : prop) => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <nav className="bg-blue-50 p-4 shadow-sm">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        {/* Logo */}
        <div className="text-gray-800 text-3xl font-semibold">
          <a href="/">{title.toUpperCase()}</a>
        </div>

        {/* Desktop Menu */}
        <div className="hidden md:flex space-x-6">
          <Link to="/" className="text-gray-700 hover:text-blue-600 transition duration-300">Home</Link>
          <Link to="/sign-up" className="text-gray-700 hover:text-blue-600 transition duration-300">SignUp</Link>
          <Link to="/services" className="text-gray-700 hover:text-blue-600 transition duration-300">Services</Link>
          <Link to="/contact" className="text-gray-700 hover:text-blue-600 transition duration-300">Contact</Link>
        </div>

        {/* Mobile Menu Button (Hamburger Icon) */}
        <button
          className="md:hidden text-gray-700"
          onClick={toggleMenu}
        >
          <svg
            className="w-6 h-6"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M4 6h16M4 12h16M4 18h16"
            />
          </svg>
        </button>
      </div>

      {/* Mobile Menu */}
      {isMenuOpen && (
        <div className="md:hidden flex flex-col items-center space-y-4 mt-4 bg-blue-100 p-4 rounded-lg shadow-md">
          <Link to="/" className="text-gray-700">Home</Link>
          <Link to="/about" className="text-gray-700">About</Link>
          <Link to="/services" className="text-gray-700">Services</Link>
          <Link to="/contact" className="text-gray-700">Contact</Link>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
