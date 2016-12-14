class AnimalsController < ApplicationController
  before_action :authenticate_user!, except: [:index, :show, :for_adoption]
  before_action :set_animal, only: [:delete, :update]

  def index
    @animals = animal_params[:categorie].nil? ?
        Animal.all :
        Animal.all.select do |animal|
          animal.categorie == animal_params[:categorie]
        end
  end

  def for_adoption
    @animals = Animal.all.select do |animal| animal.adoption.nil? end
    @animals = @animals.select do |animal|
      animal.categorie == animal_params[:categorie]
    end if !animal_params[:categorie].nil?
    render :index
  end

  def show
    @animal = Animal.find(animal_params[:id])
  end

  def create
    @user = current_user
    @animal = Animal.new(animal_params.merge({user: @user}))
    if @animal.save!
      render :create
    else
      render template: 'shared/errors.json', locals: { errors: @animal.errors }
    end
  end

  def update
    if @animal.update(update_params)
      render :show
    else
      render template: 'shared/errors.json', locals: { errors: @animal.errors }
    end
  end

  def delete
    @animal.destroy
    render json: { success: true }
  end


  private

  def update_params
    {
      name: animal_params[:name]
    }
  end

  def set_animal
    @animal = Animal.find_by(id: animal_params[:id])
  end

  def animal_params
    params.permit(:id, :name, :categorie, :user, :description)
  end
end
