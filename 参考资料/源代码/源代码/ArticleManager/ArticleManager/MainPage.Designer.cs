namespace ArticleManager
{
    partial class MainPage
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.Author_button = new System.Windows.Forms.Button();
            this.Reviewer_button = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // Author_button
            // 
            this.Author_button.Location = new System.Drawing.Point(71, 69);
            this.Author_button.Name = "Author_button";
            this.Author_button.Size = new System.Drawing.Size(241, 30);
            this.Author_button.TabIndex = 0;
            this.Author_button.Text = "添加/更新作者";
            this.Author_button.UseVisualStyleBackColor = true;
            this.Author_button.Click += new System.EventHandler(this.button1_Click);
            // 
            // Reviewer_button
            // 
            this.Reviewer_button.Location = new System.Drawing.Point(71, 154);
            this.Reviewer_button.Name = "Reviewer_button";
            this.Reviewer_button.Size = new System.Drawing.Size(241, 30);
            this.Reviewer_button.TabIndex = 1;
            this.Reviewer_button.Text = "添加/更新审稿人";
            this.Reviewer_button.UseVisualStyleBackColor = true;
            this.Reviewer_button.Click += new System.EventHandler(this.button1_Click_1);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(71, 239);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(241, 30);
            this.button1.TabIndex = 2;
            this.button1.Text = "更新文章";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click_2);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(71, 324);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(241, 30);
            this.button2.TabIndex = 3;
            this.button2.Text = "接收文章";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(71, 409);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(241, 30);
            this.button3.TabIndex = 4;
            this.button3.Text = "检查状态";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // MainPage
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(387, 503);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.Reviewer_button);
            this.Controls.Add(this.Author_button);
            this.Name = "MainPage";
            this.Text = "文章管理系统";
            this.Load += new System.EventHandler(this.主页_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button Author_button;
        private System.Windows.Forms.Button Reviewer_button;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;
    }
}

