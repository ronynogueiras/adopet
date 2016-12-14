class AddDescriptionToAnimal < ActiveRecord::Migration[5.0]
  def change
    add_column :animals, :description, :string
  end
end
