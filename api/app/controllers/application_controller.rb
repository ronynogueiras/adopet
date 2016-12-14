class ApplicationController < ActionController::API
  include DeviseTokenAuth::Concerns::SetUserByToken
  before_action :set_request_to_json

  private

  def set_request_to_json
    request.format = :json
  end
end
