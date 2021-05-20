namespace ArticleManager
{
    partial class AuthorForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.AddAuthor_button = new System.Windows.Forms.Button();
            this.UpdateAuthor_button = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // AddAuthor_button
            // 
            this.AddAuthor_button.Location = new System.Drawing.Point(68, 57);
            this.AddAuthor_button.Name = "AddAuthor_button";
            this.AddAuthor_button.Size = new System.Drawing.Size(155, 38);
            this.AddAuthor_button.TabIndex = 0;
            this.AddAuthor_button.Text = "添加作者";
            this.AddAuthor_button.UseVisualStyleBackColor = true;
            this.AddAuthor_button.Click += new System.EventHandler(this.AddAuthor_button_Click);
            // 
            // UpdateAuthor_button
            // 
            this.UpdateAuthor_button.Location = new System.Drawing.Point(68, 153);
            this.UpdateAuthor_button.Name = "UpdateAuthor_button";
            this.UpdateAuthor_button.Size = new System.Drawing.Size(155, 38);
            this.UpdateAuthor_button.TabIndex = 1;
            this.UpdateAuthor_button.Text = "更新作者";
            this.UpdateAuthor_button.UseVisualStyleBackColor = true;
            this.UpdateAuthor_button.Click += new System.EventHandler(this.UpdateAuthor_button_Click);
            // 
            // AuthorForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(282, 253);
            this.Controls.Add(this.UpdateAuthor_button);
            this.Controls.Add(this.AddAuthor_button);
            this.Name = "AuthorForm";
            this.Text = "添加/更新作者";
            this.Load += new System.EventHandler(this.AuthorForm_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button AddAuthor_button;
        private System.Windows.Forms.Button UpdateAuthor_button;
    }
}