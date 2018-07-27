class RegistrationsController < ::DeviseTokenAuth::RegistrationsController
  def sign_up_params
    params.permit(:name, :email, :password, :password_confirmation)
  end
end
