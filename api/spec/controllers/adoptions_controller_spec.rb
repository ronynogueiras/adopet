require 'rails_helper'

RSpec.describe AdoptionsController, type: :controller do

  let(:user){ create(:user) }
  let(:new_owner){ create(:user) }

  let(:valid_attributes) do
    {
      name: Faker::Internet.name,
      categorie: 'cachorro',
      description: 'description'
    }
  end

  let(:animal){ create(:animal, user: user) }

  let(:adoption) do
    create(:adoption, old_owner: animal.user, new_owner: new_owner, animal: animal)
  end

  describe 'GET #index' do
    before do
      @auth_headers = user.create_new_auth_token
    end
    it 'assigns all animals as @animals' do
      get :index
      expect(assigns(:adoptions)).to eq([adoption])
    end
  end

  describe 'GET #show' do
    before do
      @auth_headers = user.create_new_auth_token
    end
    it 'assigns all animal as @animal' do
      get :show, params: { id: adoption.to_param }
      expect(assigns(:adoption)).to eq(adoption)
    end
  end

  describe 'POST #create' do
    before do
      @auth_headers = new_owner.create_new_auth_token
    end
    it 'deletes a Animal' do
      expect do
        post :create, {animal: animal.to_param}
                        .merge({ format: :json })
                        .merge(@auth_headers), {}
      end.to change(Animal, :count).by(1)
    end
  end
end
