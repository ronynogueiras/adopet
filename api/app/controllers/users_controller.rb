class UsersController < ApplicationController
  before_action :authenticate_user!
  before_action :set_user, only: [:animals, :update]

  def update
    if @user.update(update_params)
      render :show
    else
      render template: 'shared/errors.json', locals: { errors: @user.errors }
    end
  end

  def animals
    @animals = @user.animals
    render template: 'animals/index'
  end

  private

  def update_params
    {
      name: user_params[:name]
    }
  end

  def set_user
    @user = User.find_by(id: user_params[:id])
  end

  def user_params
    params.permit(:id, :name)
  end
end
