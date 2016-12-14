Rails.application.routes.draw do
  mount_devise_token_auth_for 'User', at: 'auth',
                                      controllers:{
                                        registrations: 'registrations'
                                      }


  get 'users/:id/animals', to: 'users#animals'
  get 'for_adoption', to: 'animals#for_adoption'
  resources :animals, except: [:new, :edit]
  resources :users, only: [:update]
  resources :adoptions, only: [:index, :create, :show]
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
