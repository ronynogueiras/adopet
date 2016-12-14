class AdoptionsController < ApplicationController
  before_action :authenticate_user!, except: [:index]
  before_action :set_animal, only: [:create]
  before_action :set_new_owner, only: [:create]
  rescue_from ActiveRecord::RecordNotUnique, with: :animal_already_adopted

  def index
    @adoptions = Adoption.all
  end

  def create
    @adoption = Adoption.new(create_params)
    if @adoption.save
      render :show
    else
      render template: 'shared/errors.json', locals: { errors: @adoption.errors }
    end
  end

  protected

  def animal_already_adopted
    render template: 'shared/errors.json', locals: {
                                              errors: "Animal já adotado !" }
  end

  private

  def create_params
    {
      new_owner: @user,
      animal: @animal,
      old_owner: @animal.user
    }
  end

  def set_animal
    @animal = Animal.find_by(id: adoption_params[:animal])
  end

  def set_new_owner
    @user = current_user
  end

  def adoption_params
    params.permit(:animal)
  end
end
