import React, { useState } from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
interface IFormInput {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  gender: string;
  profileImage: File | null; // Optional field for the profile image
}

const SignUpForm: React.FC = () => {
  const [imagePreview, setImagePreview] = useState<string | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  // Handle opening the modal
  const openModal = () => setIsModalOpen(true);

  // Handle closing the modal
  const closeModal = () => setIsModalOpen(false);

  // Define validation schema with Yup
  const validationSchema = Yup.object().shape({
    username: Yup.string()
      .matches(
        /^[a-z]{5,20}|[a-z]{5,20}(_[a-z]{1,20})?$/,
        "Only lowercase letters 'a' to 'b' and underscore '_' are allowed with max 20 characters"
      )
      .required("User name cannot be blank"),
    firstName: Yup.string().required("First name cannot be blank"),
    lastName: Yup.string().required("Last name cannot be blank"),
    email: Yup.string()
      .email("Email is not valid")
      .required("Email cannot be blank"),
    password: Yup.string().required("Password cannot be null"),
    gender: Yup.string().required("Gender cannot be blank"),
    profileImage: Yup.mixed().optional(), // Optional image field
  });

  // Setup the useForm hook with validation schema
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
  } = useForm<IFormInput>({
    resolver: yupResolver(validationSchema),
  });

  // Handle file change (for image preview)
  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      setImagePreview(URL.createObjectURL(file)); // Display preview
      setValue("profileImage", file); // Set file value to react-hook-form
    }
  };

  // Submit handler function
  const onSubmit: SubmitHandler<IFormInput> = (data: IFormInput) => {
    const formData = new FormData();
    const userData = JSON.stringify({
      first_name: data.firstName,
      last_name: data.lastName,
      email: data.email,
      password: data.password,
      username: data.username,
      gender: data.gender,
    });
    formData.append("user", userData);
    if (data.profileImage) {
      formData.append("image", data.profileImage);
    }
    fetch("your-api-endpoint", {
      method: "POST",
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Form submitted successfully:", data);
      })
      .catch((error) => {
        console.error("Error submitting form:", error);
      });
  };

  return (
    <div className="container mx-auto p-6 max-w-lg">
      <h2 className="text-2xl font-semibold mb-4">Sign Up</h2>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        {/* Username Field */}
        <div>
          <label htmlFor="username" className="block text-gray-700">
            Username
          </label>
          <input
            id="username"
            {...register("username")}
            type="text"
            className="w-full p-2 border rounded"
          />
          {errors.username && (
            <p className="text-red-500 text-sm">{errors.username.message}</p>
          )}
        </div>

        {/* First Name Field */}
        <div>
          <label htmlFor="firstName" className="block text-gray-700">
            First Name
          </label>
          <input
            id="firstName"
            {...register("firstName")}
            type="text"
            className="w-full p-2 border rounded"
          />
          {errors.firstName && (
            <p className="text-red-500 text-sm">{errors.firstName.message}</p>
          )}
        </div>

        {/* Last Name Field */}
        <div>
          <label htmlFor="lastName" className="block text-gray-700">
            Last Name
          </label>
          <input
            id="lastName"
            {...register("lastName")}
            type="text"
            className="w-full p-2 border rounded"
          />
          {errors.lastName && (
            <p className="text-red-500 text-sm">{errors.lastName.message}</p>
          )}
        </div>

        {/* Email Field */}
        <div>
          <label htmlFor="email" className="block text-gray-700">
            Email
          </label>
          <input
            id="email"
            {...register("email")}
            type="email"
            className="w-full p-2 border rounded"
          />
          {errors.email && (
            <p className="text-red-500 text-sm">{errors.email.message}</p>
          )}
        </div>

        {/* Password Field */}
        <div>
          <label htmlFor="password" className="block text-gray-700">
            Password
          </label>
          <input
            id="password"
            {...register("password")}
            type="password"
            className="w-full p-2 border rounded"
          />
          {errors.password && (
            <p className="text-red-500 text-sm">{errors.password.message}</p>
          )}
        </div>

        {/* Gender Field */}
        <div>
          <label htmlFor="gender" className="block text-gray-700">
            Gender
          </label>
          <select
            id="gender"
            {...register("gender")}
            className="w-full p-2 border rounded"
          >
            <option value="">Select Gender</option>
            <option value="male">Male</option>
            <option value="female">Female</option>
            <option value="other">Other</option>
          </select>
          {errors.gender && (
            <p className="text-red-500 text-sm">{errors.gender.message}</p>
          )}
        </div>

        {/* Image Upload Field */}
        <div>
          {/* Custom File Upload Button */}
          <div className="relative">
            <button
              type="button"
              className="w-full px-4 py-2 text-white bg-green-600 rounded-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 flex items-center justify-center space-x-2"
              onClick={() => document.getElementById("profileImage")?.click()}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                fill="currentColor"
                className="bi bi-upload"
                viewBox="0 0 16 16"
              >
                <path d="M4.5 7a.5.5 0 0 1 .5-.5H7V1.707l2.646 2.647a.5.5 0 0 1 .708-.708L8 0 .707 8.707a.5.5 0 0 1 .708.708L7 1.707V6.5h2a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-.5.5h-6a.5.5 0 0 1-.5-.5V7z" />
              </svg>
              <span>{imagePreview ? "Change Image" : "Upload Image"}</span>
            </button>
            {/* Hidden file input */}
            <input
              id="profileImage"
              type="file"
              className="absolute inset-0 opacity-0 cursor-pointer"
              accept="image/*"
              onChange={handleFileChange}
            />
          </div>

          {/* Image Preview */}
          {imagePreview && (
            <div className="grid place-items-center mt-2">
                {
                    imagePreview &&               
                    <img
                    src={imagePreview}
                    alt="preview"
                    className="w-40 h-40 object-cover rounded cursor-pointer"
                    onClick={openModal}
                  />
                }

            </div>
          )}

          {/* Image Preview Modal */}
          {isModalOpen && (
            <div
              className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
              onClick={closeModal}
            >
              <div
                className="relative bg-white p-4 rounded-md lg:m-96"
                onClick={(e) => e.stopPropagation()} // Prevent closing when clicking inside modal
              >
                <button
                  className="absolute top-2 right-2 text-gray-500 text-3xl md:text-5xl"
                  onClick={closeModal}
                >
                  &times;
                </button>
                {imagePreview && (
                  <img
                    src={imagePreview}
                    alt="Full-size preview"
                    className="max-w-full object-contain"
                  />
                )}
              </div>
            </div>
          )}
        </div>

        {/* Submit Button */}
        <div>
          <button
            type="submit"
            className="w-full p-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
          >
            Sign Up
          </button>
        </div>
      </form>
    </div>
  );
};

export default SignUpForm;
