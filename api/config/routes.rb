Rails.application.routes.draw do
  mount_devise_token_auth_for 'User', at: 'auth',
                                      controllers:{
                                        registrations: 'registrations'
                                      }
  resources :animals, except: [:new, :edit]
  resources :users, only: [:update]
  resources :adoptions, only: [:index, :create, :show]
  get 'users/:id/animals', to: 'users#animals'

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
