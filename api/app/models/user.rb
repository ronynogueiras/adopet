class User < ActiveRecord::Base
  # Include default devise modules.
  devise :database_authenticatable, :registerable,
          :recoverable, :rememberable, :trackable, :validatable,
          :omniauthable

  has_many :animals
  has_many :adoptions_realised, foreign_key: "old_owner_id", class_name: "Adoption"
  has_many :adoptions, foreign_key: "new_owner_id", class_name: "Adoption"
  validates :name, presence: true

  include DeviseTokenAuth::Concerns::User
end
