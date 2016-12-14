class AddIndexToAnimalAtAdoptions < ActiveRecord::Migration[5.0]
  def change
    remove_index :adoptions, :animal_id
    add_index :adoptions, :animal_id, unique: true
  end
end
