require 'rails_helper'

RSpec.describe AnimalsController, type: :controller do
  let(:user) { create(:user) }

  let(:valid_attributes) do
    {
      name: Faker::Internet.name,
      categorie: 'cachorro',
      description: 'description'
    }
  end

  let(:create_animal) do
    post :create, valid_attributes
      .merge(format: :json)
      .merge(auth_headers), {}
  end

  let(:auth_headers) { user.create_new_auth_token }

  let(:animal) { create(:animal, user: user) }

  describe 'GET #index' do
    it 'assigns all animals as animals' do
      get :index
      expect(assigns(:animals)).to eq([animal])
    end
  end

  describe 'GET #show' do
    it 'assigns all animal as animal' do
      get :show, params: { id: animal.to_param }
      expect(assigns(:animal)).to eq(animal)
    end
  end

  describe 'POST #create' do
    it 'deletes a Animal' do
      expect do
        create_animal
      end.to change(Animal, :count).by(1)
    end
  end

  describe 'PUT #update' do
    it 'updates a Animal' do
      put :update, { id: animal.to_param, name: 'louca' }
        .merge(format: :json)
        .merge(auth_headers), {}
      expect(assigns(:animal).name).to eq('louca')
    end
  end

  describe 'DELETE #delete' do
    before do
      animal
    end
    it 'deletes a Animal' do
      expect do
        delete :destroy, { id: animal.to_param }
          .merge(format: :json)
          .merge(auth_headers), {}
      end.to change(Animal, :count).by(-1)
    end
  end
end
